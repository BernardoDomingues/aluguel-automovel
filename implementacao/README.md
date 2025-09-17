# Sistema de Aluguel de Automóveis - Spring Boot

Sistema de gerenciamento de aluguel de automóveis desenvolvido com Spring Boot seguindo arquitetura MVC.

## 🚀 Guia de Instalação e Execução

### 📋 Pré-requisitos

- **Java 17 ou superior** (já instalado ✅)
- **Maven** (será instalado via Scoop)
- **PowerShell** (já disponível no Windows)

### 🔧 Instalação do Maven via Scoop

#### 1. Instalar o Scoop (Gerenciador de Pacotes)

Abra o **PowerShell como Administrador** e execute:

```powershell
# Permitir execução de scripts
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser

# Instalar o Scoop
Invoke-RestMethod -Uri https://get.scoop.sh | Invoke-Expression
```

#### 2. Instalar o Maven

```powershell
# Instalar Maven
scoop install maven

# Verificar instalação
mvn --version
```

### 🏗️ Arquitetura do Projeto

O projeto segue o padrão **MVC (Model-View-Controller)**:

- **Model**: Entidades JPA (`Automovel`, `Cliente`, `Contrato`)
- **View**: Interface via documentação Swagger
- **Controller**: REST Controllers para exposição da API
- **Service**: Camada de lógica de negócio
- **Repository**: Camada de acesso a dados

### 📁 Estrutura do Projeto

```
src/main/java/com/aluguel/
├── SistemaAluguelApplication.java    # Classe principal
├── config/
│   └── SwaggerConfig.java           # Configuração do Swagger
├── controller/                      # Controllers REST
│   ├── AutomovelController.java
│   ├── ClienteController.java
│   └── ContratoController.java
├── service/                         # Camada de serviços
│   ├── AutomovelService.java
│   ├── ClienteService.java
│   └── ContratoService.java
├── repository/                      # Repositórios JPA
│   ├── AutomovelRepository.java
│   ├── ClienteRepository.java
│   └── ContratoRepository.java
└── model/                          # Entidades JPA
    ├── Automovel.java
    ├── Cliente.java
    └── Contrato.java
```

## 🚀 Como Executar

### 1. Navegar para o Diretório do Projeto

```powershell
cd C:\Users\Domingues\aluguel\implementacao
```

### 2. Compilar o Projeto

```powershell
mvn clean compile
```

### 3. Executar a Aplicação

```powershell
mvn spring-boot:run
```

### 4. Verificar se Está Funcionando

Quando aparecer a mensagem:
```
Started SistemaAluguelApplication in X.XXX seconds
```

A aplicação estará rodando em: **http://localhost:8080**

## 🌐 Acessando a Aplicação

### 📚 Swagger UI (Documentação Interativa)
```
http://localhost:8080/swagger-ui.html
```
- Interface completa para testar todos os endpoints
- Documentação interativa da API
- Teste direto dos endpoints

### 🗄️ H2 Console (Banco de Dados)
```
http://localhost:8080/h2-console
```

**Credenciais do H2:**
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: `password`

## 📋 Endpoints da API

### 🔐 Autenticação
- `POST /api/auth/login` - Fazer login no sistema
- `POST /api/auth/validate` - Validar token de autenticação
- `POST /api/auth/logout` - Fazer logout do sistema

### 🚗 Automóveis
- `GET /api/automoveis` - Listar todos os automóveis
- `GET /api/automoveis/disponiveis` - Listar automóveis disponíveis
- `GET /api/automoveis/marca/{marca}` - Buscar por marca
- `GET /api/automoveis/modelo/{modelo}` - Buscar por modelo
- `GET /api/automoveis/ano/{ano}` - Buscar por ano
- `GET /api/automoveis/proprietario/{proprietario}` - Buscar por tipo de proprietário
- `GET /api/automoveis/faixa-preco?valorMinimo=X&valorMaximo=Y` - Buscar por faixa de preço
- `GET /api/automoveis/marca-modelo?marca=X&modelo=Y` - Buscar por marca e modelo
- `GET /api/automoveis/faixa-ano?anoInicio=X&anoFim=Y` - Buscar por faixa de ano
- `GET /api/automoveis/placa/{placa}` - Buscar por placa
- `GET /api/automoveis/matricula/{matricula}` - Buscar por matrícula
- `POST /api/automoveis` - Criar novo automóvel
- `PUT /api/automoveis/{id}` - Atualizar automóvel
- `DELETE /api/automoveis/{id}` - Excluir automóvel
- `PATCH /api/automoveis/{id}/disponivel` - Marcar como disponível
- `PATCH /api/automoveis/{id}/indisponivel` - Marcar como indisponível

### 👤 Clientes
- `GET /api/clientes` - Listar todos os clientes
- `GET /api/clientes/{id}` - Buscar por ID
- `GET /api/clientes/cpf/{cpf}` - Buscar por CPF
- `GET /api/clientes/rg/{rg}` - Buscar por RG
- `GET /api/clientes/email/{email}` - Buscar por email
- `GET /api/clientes/nome?nome=X` - Buscar por nome
- `GET /api/clientes/profissao?profissao=X` - Buscar por profissão
- `GET /api/clientes/endereco?endereco=X` - Buscar por endereço
- `POST /api/clientes` - Criar novo cliente
- `PUT /api/clientes/{id}` - Atualizar cliente
- `DELETE /api/clientes/{id}` - Excluir cliente

### 📋 Contratos
- `GET /api/contratos` - Listar todos os contratos
- `GET /api/contratos/{id}` - Buscar por ID
- `GET /api/contratos/status/{status}` - Listar por status (PENDENTE, APROVADO, REJEITADO, ATIVO, FINALIZADO, CANCELADO)
- `GET /api/contratos/tipo/{tipoContrato}` - Listar por tipo (ALUGUEL, CREDITO)
- `GET /api/contratos/automovel/{automovelId}` - Listar por automóvel
- `GET /api/contratos/usuario/{usuarioId}` - Listar por usuário
- `GET /api/contratos/pendentes` - Listar pedidos pendentes
- `GET /api/contratos/ativos-na-data?data=YYYY-MM-DD` - Listar ativos em uma data
- `GET /api/contratos/vencidos` - Listar contratos vencidos
- `POST /api/contratos/pedido` - Criar pedido de aluguel
- `PUT /api/contratos/{id}` - Atualizar contrato
- `PATCH /api/contratos/{id}/aprovar` - Aprovar pedido
- `PATCH /api/contratos/{id}/rejeitar` - Rejeitar pedido
- `PATCH /api/contratos/{id}/ativar` - Ativar contrato
- `PATCH /api/contratos/{id}/finalizar` - Finalizar contrato
- `PATCH /api/contratos/{id}/cancelar` - Cancelar contrato
- `DELETE /api/contratos/{id}` - Excluir contrato

## 🧪 Como Testar

### 1. Via Swagger UI (Recomendado)
1. Acesse: `http://localhost:8080/swagger-ui.html`
2. Clique em qualquer endpoint
3. Clique em "Try it out"
4. Preencha os dados e clique em "Execute"

### 2. Via H2 Console
1. Acesse: `http://localhost:8080/h2-console`
2. Use as credenciais acima
3. Execute queries SQL para ver os dados

### 3. Exemplo de Dados para Teste

#### Fazer Login:
```json
{
  "email": "joao@email.com",
  "senha": "123456"
}
```

#### Criar um Cliente (com senha):
```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "123456",
  "cpf": "123.456.789-00",
  "rg": "12.345.678-9",
  "endereco": "Rua das Flores, 123",
  "telefone": "(11) 99999-9999",
  "profissao": "Engenheiro",
  "empregadores": "Empresa ABC Ltda",
  "rendimentos": "Salário: R$ 5.000,00",
  "observacoes": "Cliente responsável"
}
```

#### Criar um Automóvel:
```json
{
  "matricula": "AUTO001",
  "ano": 2022,
  "marca": "Toyota",
  "modelo": "Corolla",
  "placa": "ABC1234",
  "valorAluguel": 150.00,
  "descricao": "Carro em excelente estado",
  "disponivel": true,
  "proprietario": "EMPRESA"
}
```

#### Criar um Cliente (exemplo antigo - sem senha):
```json
{
  "nome": "João Silva",
  "cpf": "123.456.789-00",
  "rg": "12.345.678-9",
  "endereco": "Rua das Flores, 123",
  "email": "joao@email.com",
  "telefone": "(11) 99999-9999",
  "profissao": "Engenheiro",
  "empregadores": "Empresa ABC Ltda",
  "rendimentos": "Salário: R$ 5.000,00",
  "observacoes": "Cliente responsável"
}
```

#### Criar um Pedido de Aluguel:
```json
{
  "automovel": {
    "id": 1
  },
  "cliente": {
    "id": 1
  },
  "dataInicio": "2024-01-01",
  "dataFim": "2024-01-31",
  "observacoes": "Pedido de aluguel para viagem"
}
```

## 🔧 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **H2 Database** (banco em memória para desenvolvimento)
- **Swagger/OpenAPI 3** (documentação da API)
- **Maven** (gerenciamento de dependências)

## ✅ Validações Implementadas

- Campos obrigatórios
- Formato de CPF (000.000.000-00)
- Formato de RG
- Formato de email válido
- Formato de placa (ABC1234 ou ABC1A23)
- Valores positivos para preços
- Conflitos de datas em contratos
- Disponibilidade de automóveis
- CPF, email e RG únicos
- Placa e matrícula únicas

## 📊 Logs

O sistema está configurado para exibir logs detalhados:
- Logs da aplicação em nível DEBUG
- Logs do Spring Web em nível DEBUG
- SQL do Hibernate formatado

## 🆘 Solução de Problemas

### Erro: "mvn não é reconhecido"
- Verifique se o Maven foi instalado corretamente
- Execute: `scoop install maven`
- Reinicie o PowerShell

### Erro de Compilação
- Execute: `mvn clean compile`
- Verifique se o Java 17+ está instalado

### Porta 8080 em uso
- Altere a porta no `application.properties`:
  ```properties
  server.port=8081
  ```

### Aplicação não inicia
- Verifique se não há outros serviços na porta 8080
- Execute: `mvn clean` e depois `mvn spring-boot:run`
