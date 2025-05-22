document.addEventListener("DOMContentLoaded", function() {
    // Define a data padrão para o campo de data (hoje)
    const hoje = new Date();
    const dataFormatada = hoje.toISOString().split('T')[0];
    document.getElementById('dataVencimento').value = dataFormatada;
});

async function gerarCodigoBarras() {
    // Obter os valores dos campos
    const dataVencimento = document.getElementById('dataVencimento').value;
    const valor = document.getElementById('valor').value;
    
    // Validar os campos
    if (!dataVencimento || !valor) {
        Toastify({
            text: "Favor, preencher todos os campos!",
            duration: 3000,
            gravity: "top", 
            position: "right",
            backgroundColor: "#f44336",
            stopOnFocus: true
        }).showToast();
        return;
    }
    
    // Mostrar o loader e desabilitar o botão
    document.getElementById('loader').style.display = 'block';
    document.getElementById('resultContent').style.display = 'none';
    document.getElementById('gerarButton').disabled = true;
    document.getElementById('btn-download').style.display = 'inline-block';
    
    console.log(JSON.stringify({
        dataVencimento: dataVencimento,
        valor: parseFloat(valor)
    }));

    try {
        // Chamar a API
        const response = await fetch('http://localhost:7071/api/barcode-generate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                dataVencimento: dataVencimento,
                valor: parseFloat(valor)
            })
        });
        
        if (!response.ok) {
            throw new Error(`Erro: ${response.status}`);
        }
        
        const data = await response.json();
        
        // Exibir o código de barras
        document.getElementById('barcodeImage').src = `data:image/png;base64,${data.imagemBase64}`;
        document.getElementById('barcodeText').textContent = data.barcode;

        Toastify({
            text: "Código de barras gerado com sucesso!",
            duration: 3000,
            gravity: "top", 
            position: "right",
            backgroundColor: "#4CAF50",
            stopOnFocus: true
        }).showToast();

        
        // Mostrar o resultado
        document.getElementById('resultContent').style.display = 'block';
    } catch (error) {
        console.error('Erro ao gerar o código de barras:', error);
        Toastify({
            text: "Erro ao gerar código de barras!",
            duration: 3000,
            gravity: "top", 
            position: "right",
            backgroundColor: "#f44336",
            stopOnFocus: true
        }).showToast();

        alert('Erro ao gerar o código de barras. Verifique o console para mais detalhes.');
    } finally {
        // Ocultar o loader e habilitar o botão
        document.getElementById('loader').style.display = 'none';
        document.getElementById('gerarButton').disabled = false;
    }
}

// Função para validar o código de barras
async function validarCodigo() {
    const barcode = document.getElementById('barcodeText').innerText;
    
    if (!barcode) {
        Toastify({
            text: "Nenhum código de barras para validar.",
            duration: 3000,
            gravity: "top", 
            position: "right",
            backgroundColor: "#2196f3",
            stopOnFocus: true
        }).showToast();
        return;
    }
    // Chamar a API de validação
    const response = await fetch('http://localhost:7073/api/barcode-validate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ barcode })
    });
    
    const result = await response.json();
    
    // Atualizar a UI com base no resultado da validação
    updateValidationUI(result);
    
}

function updateValidationUI(result){
    const barcodeResult = document.getElementById('barcodeText');
    
    // Limpar classes anteriores
    barcodeResult.classList.remove('barcode-valid', 'barcode-invalid');
    //validationMessage.classList.remove('validation-valid', 'validation-invalid');
    if (result.valido === true) {
        barcodeResult.classList.add('barcode-valid');
        Toastify({
            text: "Código de barras válido!",
            duration: 3000,
            gravity: "top", 
            position: "right",
            backgroundColor: "#4CAF50",
            stopOnFocus: true
        }).showToast();

    } else {
        barcodeResult.classList.add('barcode-invalid');

        Toastify({
            text: "Código de barras inválido!",
            duration: 3000,
            gravity: "top", 
            position: "right",
            backgroundColor: "#f44336",
            stopOnFocus: true
        }).showToast();
        // validationMessage.textContent = 'Código de barras inválido!';
        // validationMessage.classList.add('validation-invalid');
    }
}

function baixarImagem() {
    const imgElement = document.getElementById("barcodeImage");

    if (!imgElement.src) {
        Toastify({
            text: "Nenhum código de barras gerado para exportar.",
            duration: 3000,
            gravity: "top", 
            position: "right",
            backgroundColor: "#2196f3",
            stopOnFocus: true
        }).showToast();
        return;
    }

    // Criar um link temporário para download
    const link = document.createElement("a");
    link.href = imgElement.src;
    link.download = "codigo_barras.png";
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}

