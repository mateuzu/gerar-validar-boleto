# Gerador e Validador de boletos com Azure Functions + Java

Este projeto utiliza Azure Functions com Java + Maven para geração e validação de boletos com código de barras.

Criei um frontend simples em HTML, CSS e JavaScript para consumo das APIs.

---

## 📦 Tecnologias utilizadas

### 🖥️ Backend
- **Java 17**
- **Azure Functions**
- **Maven**
- **Azure Service Bus (Queue)**
- **Jackson + JavaTimeModule**
- **SLF4J + Logback (logging)**
- **ZXing (core + javase) – geração de código de barras em imagem (base64)**
- **SOLID + Clean Architecture (parcial)**

### 🌐 Frontend
- **HTML + CSS**
- **Vanilla JavaScript**
- **[ToastifyJS](https://github.com/apvarun/toastify-js)** para notificações

---

## ⚙️ Funcionalidades

### ✅ Geração de Boleto (`/api/barcode-generate`)
- Recebe data de vencimento e valor
- Gera o código de barras (44 dígitos)
- Retorna uma imagem em base64 do código
- Envia o boleto gerado para uma fila no Azure Service Bus

### ✅ Validação de Boleto (`/api/barcode-validate`)
- Recebe um código de barras de 44 dígitos
- Valida se o formato está correto
- Extrai a data de vencimento e retorna se é válido

---

## Exemplo de requisição 📌

```http
POST /api/barcode-generate
Content-Type: application/json

{
  "dataVencimento": "2025-06-01",
  "valor": 150.75
}
```

**Resposta**
```json
{
  "barcode": "01320250601150750000000000000000000000000000",
  "valor": 150.75,
  "dataVencimento": "2025-06-01",
  "dataGeracao": "2025-05-22",
  "imagemBase64": "iVBORw0KGgoAAAANSUhEUg..."
}
```

## Autor

Desenvolvido por `@mateuzu`
- [Linkedin](https://linkedin.com/in/mateus-ferreira-costa)
