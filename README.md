# Ethereum Transaction Tracker

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)]()
[![Java Version](https://img.shields.io/badge/java-17-orange)]()
[![Maven](https://img.shields.io/badge/maven-3.6+-blue)]()

## 📋 Project Overview

**Project Name:** Ethereum Transaction Tracker  
**Language:** Java 17  
**Build Tool:** Maven 3.6+  
**Purpose:** Automated transaction history retrieval and CSV export for Ethereum wallets

### Description

Traders often need detailed transaction history to track their asset movements, reconcile their portfolios, and report their holdings accurately. Many portfolio tracking applications require structured transaction data, but retrieving and organizing this data manually can be tedious.

By automating the process of fetching and categorizing Ethereum wallet transactions, this project aims to streamline transaction tracking. Users can easily export their transaction history into a CSV file, which can then be imported into portfolio management software or used for personal record-keeping.

This system is valuable for individuals and businesses managing multiple wallets, ensuring accurate reporting and improving financial tracking efficiency.

### Objective

Develop a script that retrieves transaction history for a specified Ethereum wallet address and exports it to a structured CSV file with relevant transaction details.

**Supported Transaction Types:**
- ✅ Normal ETH transfers
- ✅ Internal transactions
- ✅ ERC-20 token transfers
- ✅ ERC-721 NFT transfers

## 🏗️ Project Structure

```
crypto-transaction-tracker/
├── pom.xml                             # Maven dependencies & build configuration
├── .gitignore                          # Git ignore patterns
├── README.md                           # This file
│
└── src/
    ├── main/
    │   ├── java/com/cointracker/
    │   │   ├── Main.java               # Application entry point
    │   │   │
    │   │   ├── client/                 # API Communication Layer
    │   │   │   ├── BlockchainApiClient.java      # Strategy interface for API providers
    │   │   │   └── EtherscanApiClient.java       # Etherscan API implementation
    │   │   │
    │   │   ├── config/                 # Configuration Management
    │   │   │   └── ApiConfig.java                # Loads application.properties
    │   │   │
    │   │   ├── dto/                    # Data Transfer Objects (API responses)
    │   │   │   ├── ApiResponse.java              # Generic API response wrapper
    │   │   │   └── TransactionDto.java           # Transaction data from API
    │   │   │
    │   │   ├── model/                  # Domain Models (Business Objects)
    │   │   │   ├── Transaction.java              # Abstract base class
    │   │   │   ├── TransactionType.java          # Enum: ETH_TRANSFER, ETH_INTERNAL, ERC20, ERC721
    │   │   │   ├── NormalTransaction.java        # ETH transfer model
    │   │   │   ├── InternalTransaction.java      # Internal transaction model
    │   │   │   ├── Erc20Transaction.java         # ERC-20 token model
    │   │   │   └── Erc721Transaction.java        # ERC-721 NFT model
    │   │   │
    │   │   ├── service/                # Business Logic Layer
    │   │   │   ├── TransactionService.java       # Orchestrates transaction fetching
    │   │   │   └── TransactionMapper.java        # Maps DTO → Domain Model
    │   │   │
    │   │   └── export/                 # CSV Export Layer
    │   │       └── CsvExporter.java              # Generates CSV files
    │   │
    │   └── resources/
    │       └── application.properties            # Configuration (API keys, endpoints, rate limits)
    │
    └── test/
        └── java/com/cointracker/
            ├── MainTest.java                     # End-to-end integration tests
            ├── client/
            │   └── EtherscanApiClientTest.java   # API client integration tests
            └── service/
                └── TransactionServiceTest.java   # Service layer unit tests
```

## 🚀 Setup & Installation

### Prerequisites

Before running this project, ensure you have the following installed:

- **Java 17** or higher ([Download](https://adoptium.net/))
- **Maven 3.6+** ([Download](https://maven.apache.org/download.cgi))
- **Etherscan API Key** ([Get one free](https://etherscan.io/myapikey))

Verify installation:
```bash
java -version    # Should show Java 17+
mvn -version     # Should show Maven 3.6+
```

### Installation Steps

1. **Clone the repository:**
```bash
git clone https://github.com/swamymushini/crytpo-transaction-tracker.git
cd crytpo-transaction-tracker
```

2. **Configure API Key (Optional):**

The project includes a default API key for quick testing. For production use, set your own:

```bash
# Option 1: Environment variable (recommended)
export ETHERSCAN_API_KEY=YOUR_API_KEY_HERE

# Option 2: Edit src/main/resources/application.properties
# Change: api.etherscan.api_key=YOUR_API_KEY_HERE
```

3. **Build the project:**
```bash
mvn clean compile
```

### Running the Application

**Basic usage:**
```bash
mvn exec:java -Dexec.args="<ETHEREUM_ADDRESS>"
```

**Example with sample address:**
```bash
# Vitalik's address (has all transaction types)
mvn exec:java -Dexec.args="0xd8dA6BF26964aF9D7eEd9e03E53415D37aA96045"

# Sample address with token transfers
mvn exec:java -Dexec.args="0xa39b189482f984388a34460636fea9eb181ad1a6"
```

### Expected Output

```
================================================================================
🔍 Ethereum Transaction Tracker
================================================================================
📍 Address: 0xd8dA6BF26964aF9D7eEd9e03E53415D37aA96045
🔑 API Key: X76IWJ4V************************
================================================================================

📥 Fetching normal transactions...
   ✓ Found 127 normal transactions
📥 Fetching internal transactions...
   ✓ Found 43 internal transactions
📥 Fetching ERC-20 token transfers...
   ✓ Found 89 ERC-20 transfers
📥 Fetching ERC-721 NFT transfers...
   ✓ Found 12 ERC-721 transfers

💾 Exporting to CSV...
✅ Successfully exported 271 transactions to: eth_transactions_0xd8dA6BF2.csv

📊 Export Summary:
   • Total Transactions: 271
   • Normal ETH Transfers: 127
   • Internal Transfers: 43
   • ERC-20 Transfers: 89
   • ERC-721 NFT Transfers: 12
   • Output File: eth_transactions_0xd8dA6BF2.csv

================================================================================
✨ Transaction tracking complete!
================================================================================
```

## 📊 CSV Output Format

The generated CSV contains 10 columns:

| Column | Description | Example |
|--------|-------------|---------|
| Transaction Hash | Unique identifier | 0x1234abcd... |
| Date & Time | UTC timestamp | 2021-01-01 12:00:00 |
| From Address | Sender | 0xabc... |
| To Address | Recipient | 0xdef... |
| Transaction Type | ETH Transfer, ETH Internal, ERC-20, ERC-721 | ERC-721 |
| Asset Contract Address | Token/NFT contract | 0x22c1f... |
| Asset Symbol / Name | SYMBOL (Name) or Name (SYMBOL) | POAP (Proof of Attendance) |
| Token ID | For NFTs only | 682 |
| Value / Amount | Quantity | 1 |
| Gas Fee (ETH) | Total gas cost | 0.00042 |

## 🧪 Testing

### Run All Tests

```bash
mvn test
```

**Expected Output:**
```
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### Test Structure

The project includes 10 comprehensive tests across 3 test classes:

1. **EtherscanApiClientTest** - Integration tests with real Etherscan API
2. **TransactionServiceTest** - Unit tests with mock API client  
3. **MainTest** - End-to-end integration tests

---


## 🏛️ Architecture Decisions

### 1. **Strategy Pattern for API Providers**

**Decision:** Used Strategy Pattern with `BlockchainApiClient` interface

**Why:**
- Allows easy switching between API providers (Etherscan, Alchemy, Infura)
- Makes the codebase extensible without modifying existing code
- Enables provider-specific optimizations

**Implementation:**
```java
BlockchainApiClient apiClient = new EtherscanApiClient(config);
TransactionService service = new TransactionService(apiClient, config);
```

**Benefit:** To switch to Alchemy, just create `AlchemyApiClient` implementing the same interface.

---

### 2. **Layered Architecture (Separation of Concerns)**

**Decision:** Organized code into distinct layers: Client → Service → Export

**Why:**
- **Client Layer**: Handles all HTTP communication and API-specific logic
- **Service Layer**: Business logic (orchestration, sorting, filtering)
- **Export Layer**: Output formatting (CSV generation)

**Benefit:** 
- Each layer can be tested independently
- Changes in one layer don't affect others
- Easy to add new export formats (JSON, XML) without touching API code

---

### 3. **Block-Range Pagination (Not Page-Based)**

**Decision:** Fetch transactions using block ranges (`startblock`/`endblock`) instead of page numbers

**Why:**
- Etherscan has limitation: `page × offset ≤ 10,000`
- Page-based pagination fails for addresses with >10k transactions
- Block-range pagination is unlimited

**Implementation:**
```java
// Fetch 10k records per batch
startBlock = 0;
while (hasMore) {
    fetch(startBlock, endBlock=99999999);
    startBlock = lastBlockNumber + 1;
}
```

**Benefit:** Can fetch millions of transactions for high-activity wallets.

---

### 4. **Rate Limiting with Configurable Delays**

**Decision:** Added delays between API calls (500ms) and transaction types (2000ms)

**Why:**
- Prevents API throttling and "429 Too Many Requests" errors
- Free Etherscan API tier has rate limits (5 calls/sec)
- Configurable via `application.properties`

**Configuration:**
```properties
api.rate_limit.delay_between_calls_ms=500
api.rate_limit.delay_between_types_ms=2000
```

**Benefit:** Reliable API calls without hitting rate limits.

---

### 5. **Configuration Management with Environment Variables**

**Decision:** Centralized configuration in `application.properties` with environment variable support

**Why:**
- Keeps sensitive data (API keys) out of source code
- Easy to change configuration without recompiling
- Supports default values: `${ETHERSCAN_API_KEY:default_key}`

**Configuration:**
```properties
api.etherscan.api_key=${ETHERSCAN_API_KEY:X76IWJ4VFP9F6D15SIIEIB6Y8SM458ZMAA}
api.etherscan.batch_size=10000
```

**Benefit:** Secure and flexible configuration management.

---

---

## 🎯 Domain Models (POJOs)

The application uses a hierarchy of Plain Old Java Objects (POJOs) to represent different transaction types:

### Transaction Hierarchy

```java
Transaction (abstract base class)
├── NormalTransaction      // Regular ETH transfers
├── InternalTransaction    // Internal contract calls
├── Erc20Transaction       // ERC-20 token transfers
└── Erc721Transaction      // ERC-721 NFT transfers
```

**Key Properties:**
- `hash`: Unique transaction identifier
- `timestamp`: Unix epoch timestamp
- `from`, `to`: Sender and recipient addresses
- `value`: Transaction amount (in Wei for ETH, adjusted for tokens)
- `gasUsed`, `gasPrice`: Gas fee calculation data
- `type`: Enum (ETH_TRANSFER, ETH_INTERNAL, ERC20, ERC721)

**ERC-20 Specific:**
- `tokenSymbol`, `tokenName`: Token identification
- `tokenDecimal`: For amount conversion

**ERC-721 Specific:**
- `tokenID`: Unique NFT identifier
- `tokenName`: NFT collection name

---

## 🔌 Design Patterns Used

### 1. **Strategy Pattern**

**Interface:** `BlockchainApiClient`

**Purpose:** Allows switching between different blockchain API providers without changing business logic.

**Current Implementation:**
- `EtherscanApiClient` - Etherscan API V2 integration

**Future Implementations:**
- `AlchemyApiClient` - Alchemy API
- `InfuraApiClient` - Infura API

**Usage in Code:**
```java
// Main.java
BlockchainApiClient apiClient = new EtherscanApiClient(config);
TransactionService service = new TransactionService(apiClient, config);
```

**Benefit:** Easy to add new API providers by implementing the `BlockchainApiClient` interface.

---

### 2. **Adapter Pattern**

**Purpose:** Adapts Etherscan's REST API response format to our internal domain models.

**Components:**
- `TransactionDto` - Raw API response structure
- `TransactionMapper` - Converts DTO → Domain Model
- `Transaction` models - Clean domain objects

**Flow:**
```
Etherscan API → TransactionDto → TransactionMapper → Transaction (Domain Model)
```

**Benefit:** Isolates external API structure from internal business logic. API changes only affect the adapter layer.

---

## ⏱️ Rate Limiting & API Cooling Periods

To prevent API throttling and ensure reliable data fetching, the application implements configurable delays:

### Cooling Periods

1. **Between API Calls:** 500ms delay
   - Prevents hitting Etherscan's rate limit (5 calls/sec for free tier)
   
2. **Between Transaction Types:** 2000ms delay
   - Allows cooling period after fetching all records of one type
   - Reduces risk of "429 Too Many Requests" errors

### Configuration

These values are configurable in `application.properties`:

```properties
# Rate limiting configuration
api.rate_limit.delay_between_calls_ms=500        # Delay between pagination calls
api.rate_limit.delay_between_types_ms=2000       # Delay between transaction types
```

**Console Output:**
```
📥 Fetching normal transactions...
   ✓ Found 23 normal transactions
   ⏸️  Cooling down for 2000ms...

📥 Fetching internal transactions...
```

---

## ⚙️ Configurable Properties

All configuration is centralized in `src/main/resources/application.properties`. You may wish to customize:

### API Configuration

```properties
# API Provider Selection
api.provider=etherscan                                    # Options: etherscan (more providers coming soon)

# Etherscan API Settings
api.etherscan.base_url=https://api.etherscan.io/v2/api   # Change for testnets (e.g., goerli, sepolia)
api.etherscan.chain_id=1                                  # 1=Ethereum Mainnet, 5=Goerli, 11155111=Sepolia
api.etherscan.api_key=${ETHERSCAN_API_KEY:YOUR_KEY}     # Set via environment variable for security
```

### Pagination Settings

```properties
# Batch Size (max records per API call)
api.etherscan.batch_size=10000                           # Etherscan's maximum limit
```

### Rate Limiting

```properties
# Adjust based on your API tier
api.rate_limit.delay_between_calls_ms=500                # Free tier: 5 calls/sec → 200ms minimum
api.rate_limit.delay_between_types_ms=2000               # Cooling period between transaction types
```

### HTTP Client Settings

```properties
# Timeout configuration
http.client.connect_timeout_seconds=10                   # Connection timeout
http.client.request_timeout_seconds=30                   # Request timeout
```

### Common Customizations

| Scenario | Property to Change | Recommended Value |
|----------|-------------------|-------------------|
| Faster fetching (paid API) | `delay_between_calls_ms` | `100` (10 calls/sec) |
| Large wallet (>100k txns) | `batch_size` | `10000` (max) |
| Testnet (Goerli) | `chain_id` | `5` |
| Testnet (Sepolia) | `chain_id` | `11155111` |
| Production security | `api_key` | Use environment variable |

---
### Build Commands

```bash
# Clean build artifacts
mvn clean

# Compile source code
mvn compile

# Run all tests
mvn test

# Package as JAR
mvn package

# Run the application
mvn exec:java -Dexec.args="0xAddress"
```

## 🔒 Error Handling

### Validation

- ✅ Ethereum address format (0x + 40 hex characters)
- ✅ API key validation
- ✅ HTTP status code checking
- ✅ JSON parsing error handling

### User-Friendly Messages

```
❌ Error: Invalid Ethereum address format
   Expected: 0x followed by 40 hexadecimal characters
   Example: 0xd8dA6BF26964aF9D7eEd9e03E53415D37aA96045

💡 Possible causes:
   • Network connection issues
   • Invalid API key
   • Etherscan API rate limit exceeded
   • File write permissions issue
```

## ✨ Features

- ✅ Fetches all 4 Ethereum transaction types (Normal, Internal, ERC-20, ERC-721)
- ✅ Block-range pagination for unlimited transaction history
- ✅ Configurable rate limiting to prevent API throttling
- ✅ Strategy Pattern for easy API provider switching
- ✅ Comprehensive error handling and input validation
- ✅ CSV export with 10-column format
- ✅ Unit and integration tests (10 tests, 100% passing)
- ✅ Configuration management with environment variables

## � Sample Ethereum Addresses for Testing

```bash
# Sample address with 59 transactions (23 normal, 36 ERC-20)
mvn exec:java -Dexec.args="0xa39b189482f984388a34460636fea9eb181ad1a6"

# Vitalik Buterin's address (has all transaction types including NFTs)
mvn exec:java -Dexec.args="0xd8dA6BF26964aF9D7eEd9e03E53415D37aA96045"
```

---

## � Class Diagram

```
┌─────────────────────────────────────────┐
│          BlockchainApiClient            │  ◄─── Strategy Interface
│         (interface)                     │
├─────────────────────────────────────────┤
│ + fetchNormalTransactions()             │
│ + fetchInternalTransactions()           │
│ + fetchErc20Transactions()              │
│ + fetchErc721Transactions()             │
│ + getProviderName()                     │
└─────────────────────────────────────────┘
                    △
                    │ implements
                    │
        ┌───────────┴───────────┐
        │                       │
┌───────────────────┐   ┌─────────────────┐
│ EtherscanApiClient│   │ AlchemyApiClient│  (future)
├───────────────────┤   └─────────────────┘
│ - config          │
│ - httpClient      │
│ - objectMapper    │
├───────────────────┤
│ + fetchAllPages() │
│ + makeApiCall()   │
└───────────────────┘
         │
         │ uses
         ▼
┌─────────────────┐       ┌──────────────────┐
│ TransactionDto  │◄──────│   ApiResponse<T> │  ◄─── DTOs (API Layer)
├─────────────────┤       ├──────────────────┤
│ - hash          │       │ - status         │
│ - blockNumber   │       │ - message        │
│ - timestamp     │       │ - result         │
│ - from, to      │       └──────────────────┘
│ - value         │
│ - gasPrice      │
│ - gasUsed       │
│ - tokenSymbol   │
│ - tokenName     │
│ - tokenID       │
└─────────────────┘
         │
         │ mapped by
         ▼
┌──────────────────┐
│TransactionMapper │  ◄─── Adapter Pattern
├──────────────────┤
│+ toNormalTxn()   │
│+ toInternalTxn() │
│+ toErc20Txn()    │
│+ toErc721Txn()   │
└──────────────────┘
         │
         │ produces
         ▼
┌─────────────────────┐
│    Transaction      │  ◄─── Domain Models (POJOs)
│    (abstract)       │
├─────────────────────┤
│ - hash              │
│ - timestamp         │
│ - from, to          │
│ - value             │
│ - gasFee            │
│ - type              │
├─────────────────────┤
│ + toCsvRow()        │
│ + getFormattedDate()│
└─────────────────────┘
          △
          │ extends
          │
    ┌─────┴─────┬──────────┬──────────┐
    │           │          │          │
┌───────────┐ ┌──────────┐ ┌────────┐ ┌─────────┐
│  Normal   │ │ Internal │ │ Erc20  │ │ Erc721  │
│Transaction│ │Transaction│ │Transaction│ │Transaction│
└───────────┘ └──────────┘ └────────┘ └─────────┘
                                │          │
                                │          ├─ tokenID
                                │          └─ tokenName
                                │
                                ├─ tokenSymbol
                                ├─ tokenName
                                └─ tokenDecimal

┌──────────────────────┐
│ TransactionService   │  ◄─── Business Logic
├──────────────────────┤
│ - apiClient          │
│ - config             │
├──────────────────────┤
│ + getAllTransactions()│
└──────────────────────┘
         │
         │ uses
         ▼
┌──────────────────┐
│   CsvExporter    │  ◄─── Export Layer
├──────────────────┤
│ + exportToCsv()  │
│ + exportWithSummary()│
└──────────────────┘

┌──────────────────┐
│   ApiConfig      │  ◄─── Configuration
├──────────────────┤
│ - properties     │
├──────────────────┤
│ + getApiKey()    │
│ + getBatchSize() │
│ + getRateLimit() │
└──────────────────┘
```

---

**Built with ❤️ using Java 17, by Gopal for CoinTracker ❤️**
