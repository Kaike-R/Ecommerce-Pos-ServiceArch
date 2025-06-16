import http from 'k6/http';
import { check, sleep } from 'k6';
import { Rate } from 'k6/metrics';

// Métricas customizadas
export const errorRate = new Rate('errors');

// Configurações do teste
export const options = {
    stages: [
        { duration: '2m', target: 10 },   // Ramp-up para 10 usuários em 2 minutos
        { duration: '5m', target: 10 },   // Mantém 10 usuários por 5 minutos
        { duration: '2m', target: 20 },   // Aumenta para 20 usuários em 2 minutos
        { duration: '5m', target: 20 },   // Mantém 20 usuários por 5 minutos
        { duration: '2m', target: 0 },    // Ramp-down para 0 usuários em 2 minutos
    ],
    thresholds: {
        http_req_duration: ['p(95)<2000'], // 95% das requisições devem ser < 2s
        http_req_failed: ['rate<0.1'],     // Taxa de erro < 10%
        errors: ['rate<0.1'],              // Taxa de erro customizada < 10%
    },
};

const BASE_URL = 'http://localhost:8080/v1/order-service/api';

// Dados de teste variados
const PRODUCTS = [
    { productId: 31, quantity: 21 },
    { productId: 12, quantity: 13 },
    { productId: 25, quantity: 5 },
    { productId: 18, quantity: 8 },
    { productId: 42, quantity: 15 },
];

const PAYMENT_METHODS = ['pix', 'credit_card', 'debit_card'];
const CARD_BRANDS = ['visa', 'mastercard', 'amex'];
const CITIES = ['São Paulo', 'Rio de Janeiro', 'Belo Horizonte', 'Salvador', 'Brasília'];
const STATES = ['SP', 'RJ', 'MG', 'BA', 'DF'];

// Função para gerar dados aleatórios
function getRandomUserId() {
    return Math.floor(Math.random() * 100) + 1;
}

function getRandomProducts() {
    const numProducts = Math.floor(Math.random() * 3) + 1; // 1-3 produtos
    const selectedProducts = [];

    for (let i = 0; i < numProducts; i++) {
        const product = PRODUCTS[Math.floor(Math.random() * PRODUCTS.length)];
        selectedProducts.push({
            productId: product.productId,
            quantity: Math.floor(Math.random() * product.quantity) + 1
        });
    }

    return selectedProducts;
}

function getRandomPaymentMethod() {
    return PAYMENT_METHODS[Math.floor(Math.random() * PAYMENT_METHODS.length)];
}

function getRandomCard() {
    return {
        holderName: `User ${Math.floor(Math.random() * 1000)}`,
        number: `${Math.floor(Math.random() * 9000000000) + 1000000000}`,
        expiryMonth: String(Math.floor(Math.random() * 12) + 1).padStart(2, '0'),
        expiryYear: String(Math.floor(Math.random() * 10) + 25),
        cvv: String(Math.floor(Math.random() * 900) + 100),
        brand: CARD_BRANDS[Math.floor(Math.random() * CARD_BRANDS.length)]
    };
}

function getRandomAddress() {
    const cityIndex = Math.floor(Math.random() * CITIES.length);
    return {
        address: `Rua ${Math.floor(Math.random() * 999) + 1}, ${Math.floor(Math.random() * 999) + 1}`,
        city: CITIES[cityIndex],
        state: STATES[cityIndex],
        zip: String(Math.floor(Math.random() * 90000000) + 10000000).substring(0, 8)
    };
}

// Headers padrão
const headers = {
    'Content-Type': 'application/json',
    'Accept': 'application/json',
};

export default function () {
    const userId = getRandomUserId();
    let orderId = null;

    // STEP 1: Criar pedido
    console.log(`🛒 Criando pedido para usuário ${userId}`);

    const orderPayload = {
        userId: userId,
        items: getRandomProducts()
    };

    const createOrderResponse = http.post(
        `${BASE_URL}/create`,
        JSON.stringify(orderPayload),
        { headers }
    );

    const createOrderSuccess = check(createOrderResponse, {
        'create order - status is 200 or 201': (r) => r.status === 200 || r.status === 201,
        'create order - response time < 2s': (r) => r.timings.duration < 2000,
        'create order - has response body': (r) => r.body && r.body.length > 0,
    });

    if (!createOrderSuccess) {
        errorRate.add(1);
        console.error(`❌ Falha ao criar pedido: ${createOrderResponse.status} - ${createOrderResponse.body}`);
        return;
    }

    // Extrair orderId da resposta (assumindo que vem no JSON response)
    try {
        const orderData = JSON.parse(createOrderResponse.body);
        orderId = orderData.orderId || orderData.id || Math.floor(Math.random() * 1000) + 1;
    } catch (e) {
        orderId = Math.floor(Math.random() * 1000) + 1; // Fallback
    }

    console.log(`✅ Pedido ${orderId} criado com sucesso`);

    // Pausa entre requisições (simula tempo de processamento do usuário)
    sleep(1 + Math.random() * 2); // 1-3 segundos

    // STEP 2: Processar pagamento
    console.log(`💳 Processando pagamento para pedido ${orderId}`);

    const paymentMethod = getRandomPaymentMethod();
    const paymentPayload = {
        orderId: orderId,
        amount: (Math.random() * 999 + 1).toFixed(2), // Valor aleatório entre 1-1000
        paymentMethod: paymentMethod,
        card: getRandomCard()
    };

    const payOrderResponse = http.post(
        `${BASE_URL}/pay`,
        JSON.stringify(paymentPayload),
        { headers }
    );

    const payOrderSuccess = check(payOrderResponse, {
        'pay order - status is 200 or 201': (r) => r.status === 200 || r.status === 201,
        'pay order - response time < 3s': (r) => r.timings.duration < 3000,
        'pay order - has response body': (r) => r.body && r.body.length > 0,
    });

    if (!payOrderSuccess) {
        errorRate.add(1);
        console.error(`❌ Falha no pagamento: ${payOrderResponse.status} - ${payOrderResponse.body}`);
        return;
    }

    console.log(`✅ Pagamento do pedido ${orderId} processado`);

    sleep(0.5 + Math.random()); // 0.5-1.5 segundos

    // STEP 3: Processar envio
    console.log(`🚚 Processando envio para pedido ${orderId}`);

    const address = getRandomAddress();
    const shipPayload = {
        orderId: orderId,
        userId: userId,
        address: address.address,
        city: address.city,
        state: address.state,
        zip: address.zip
    };

    const shipOrderResponse = http.post(
        `${BASE_URL}/ship`,
        JSON.stringify(shipPayload),
        { headers }
    );

    const shipOrderSuccess = check(shipOrderResponse, {
        'ship order - status is 200 or 201': (r) => r.status === 200 || r.status === 201,
        'ship order - response time < 2s': (r) => r.timings.duration < 2000,
        'ship order - has response body': (r) => r.body && r.body.length > 0,
    });

    if (!shipOrderSuccess) {
        errorRate.add(1);
        console.error(`❌ Falha no envio: ${shipOrderResponse.status} - ${shipOrderResponse.body}`);
        return;
    }

    console.log(`✅ Envio do pedido ${orderId} processado com sucesso!`);

    // Pausa final entre iterações
    sleep(1 + Math.random() * 3); // 1-4 segundos
}

// Função executada no final do teste
export function handleSummary(data) {
    console.log('📊 RESUMO DO TESTE DE CARGA:');
    console.log(`Total de requisições: ${data.metrics.http_reqs.count}`);
    console.log(`Requisições com falha: ${data.metrics.http_req_failed.count} (${(data.metrics.http_req_failed.rate * 100).toFixed(2)}%)`);
    console.log(`Duração média das requisições: ${data.metrics.http_req_duration.avg.toFixed(2)}ms`);
    console.log(`P95 das requisições: ${data.metrics['http_req_duration{p(95)}'].toFixed(2)}ms`);

    return {
        'summary.json': JSON.stringify(data, null, 2),
    };
}