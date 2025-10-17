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
    │   │   │   ├── BlockchainApiClient.java      # Strategy interface
    │   │   │   └── EtherscanApiClient.java       # Etherscan implementation
    │   │   │
    │   │   ├── config/                 # Configuration Management
    │   │   │   └── ApiConfig.java                # Properties loader
    │   │   │
    │   │   ├── dto/                    # Data Transfer Objects
    │   │   │   ├── ApiResponse.java              # API response wrapper
    │   │   │   └── TransactionDto.java           # Transaction data from API
    │   │   │
    │   │   ├── model/                  # Domain Models (POJOs)
    │   │   │   ├── Transaction.java              # Abstract base class
    │   │   │   ├── TransactionType.java          # Enum
    │   │   │   ├── NormalTransaction.java
    │   │   │   ├── InternalTransaction.java
    │   │   │   ├── Erc20Transaction.java
    │   │   │   └── Erc721Transaction.java
    │   │   │
    │   │   ├── service/                # Business Logic Layer
    │   │   │   ├── TransactionService.java
    │   │   │   └── TransactionMapper.java
    │   │   │
    │   │   └── export/                 # CSV Export Layer
    │   │       └── CsvExporter.java
    │   │
    │   └── resources/
    │       └── application.properties            # Configuration
    │
    └── test/
        └── java/com/cointracker/
            ├── MainTest.java
            ├── client/
            │   └── EtherscanApiClientTest.java
            └── service/
                └── TransactionServiceTest.java
```

## 🚀 Setup & Installation

### Prerequisites

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

**Example:**
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
🔌 API Provider: etherscan
📍 Address: 0xd8dA6BF26964aF9D7eEd9e03E53415D37aA96045
🔑 API Key: X76IWJ4V************************
================================================================================

📥 Fetching normal transactions...
   ✓ Found 127 normal transactions
   ⏸️  Cooling down for 2000ms...

📥 Fetching internal transactions...
   ✓ Found 43 internal transactions
   ⏸️  Cooling down for 2000ms...

📥 Fetching ERC-20 token transfers...
   ✓ Found 89 ERC-20 transfers
   ⏸️  Cooling down for 2000ms...

📥 Fetching ERC-721 NFT transfers...
   ✓ Found 12 ERC-721 transfers

💾 Exporting to CSV...
✅ Successfully exported 271 transactions

📊 Export Summary:
   • Total Transactions: 271
   • Normal ETH Transfers: 127
   • Internal Transfers: 43
   • ERC-20 Transfers: 89
   • ERC-721 NFT Transfers: 12
   • Output File: 0xd8dA6BF26964aF9D7eEd9e03E53415D37aA96045

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

### Expected Output

```
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

## 🏛️ Architecture Decisions

### 1. **Strategy Pattern for API Providers**

**Decision:** Used Strategy Pattern with `BlockchainApiClient` interface

**Why:**
- Allows easy switching between API providers (Etherscan, Alchemy, Infura)
- Makes the codebase extensible without modifying existing code

**Implementation:**
```java
BlockchainApiClient apiClient = new EtherscanApiClient(config);
TransactionService service = new TransactionService(apiClient, config);
```

---

### 2. **Adapter Pattern**

**Decision:** `EtherscanApiClient` acts as an adapter between Etherscan API and our domain models

**Why:**
- Isolates external API structure from internal business logic
- API changes only affect the adapter layer
- Clean separation: API responses (DTOs) → Domain Models (POJOs)

---

### 3. **Block-Range Pagination (Not Page-Based)**

**Decision:** Fetch transactions using block ranges (`startblock`/`endblock`) instead of page numbers

**Why:**
- Etherscan has limitation: `page × offset ≤ 10,000`
- Block-range pagination is unlimited and more reliable

**Implementation:**
```java
// Fetch 10k records per batch
startBlock = 0;
while (hasMore) {
    fetch(startBlock, endBlock=99999999);
    startBlock = lastBlockNumber + 1;
}
```

---

### 4. **Rate Limiting with Configurable Delays**

**Decision:** Added delays between API calls (500ms) and transaction types (2000ms)

**Why:**
- Prevents API throttling and "429 Too Many Requests" errors
- Free Etherscan API tier has rate limits (5 calls/sec)
- Configurable via `application.properties`

---

## 🎨 Design Patterns Used

### 1. **Strategy Pattern**
- **Interface**: `BlockchainApiClient`
- **Implementation**: `EtherscanApiClient`
- **Purpose**: Easy switching between API providers

### 2. **Adapter Pattern**
- **Adapter**: `EtherscanApiClient` adapts Etherscan REST API
- **Purpose**: Converts external API format to internal domain models

### 3. **POJO Models (Plain Old Java Objects)**
- **Models**: `NormalTransaction`, `InternalTransaction`, `Erc20Transaction`, `Erc721Transaction`
- **Base Class**: Abstract `Transaction` class
- **Purpose**: Clean domain objects separate from API responses

---

## ⏱️ Rate Limiting & Cooling Periods

The application implements configurable delays to prevent API throttling:

### Cooling Periods

1. **Between API Calls:** 500ms delay (prevents rate limit)
2. **Between Transaction Types:** 2000ms delay (cooling period)

### Configuration

```properties
api.rate_limit.delay_between_calls_ms=500
api.rate_limit.delay_between_types_ms=2000
```

**Console Output:**
```
📥 Fetching normal transactions...
   ✓ Found 23 normal transactions
   ⏸️  Cooling down for 2000ms...
```

---

## ⚙️ Configurable Properties

All configuration is in `src/main/resources/application.properties`:

```properties
# API Provider Selection
api.provider=etherscan

# Etherscan Configuration
api.etherscan.base_url=https://api.etherscan.io/v2/api
api.etherscan.chain_id=1
api.etherscan.api_key=${ETHERSCAN_API_KEY:X76IWJ4VFP9F6D15SIIEIB6Y8SM458ZMAA}

# Pagination
api.etherscan.batch_size=10000

# Rate Limiting
api.rate_limit.delay_between_calls_ms=500
api.rate_limit.delay_between_types_ms=2000

# HTTP Timeouts
http.client.connect_timeout_seconds=10
http.client.request_timeout_seconds=30
```

### Properties You May Want to Change:

| Property | Default | When to Change |
|----------|---------|----------------|
| `api.etherscan.api_key` | Default key | **Use your own for production** |
| `api.rate_limit.delay_between_calls_ms` | 500ms | Increase if hitting rate limits |
| `api.rate_limit.delay_between_types_ms` | 2000ms | Adjust based on API tier |
| `api.etherscan.batch_size` | 10000 | Decrease for faster initial response |
| `api.etherscan.chain_id` | 1 (Mainnet) | Change for testnets (5=Goerli, 11155111=Sepolia) |

---

## 📐 Assumptions Made

1. **API Availability**: Etherscan API is available and returns valid JSON
2. **Transaction Ordering**: Sorts by timestamp (ascending) after fetching
3. **Gas Fee Calculation**: Gas fee = `gasUsed × gasPrice`
4. **Network**: Targets Ethereum mainnet (chainid=1)
5. **CSV Filename**: Uses the Ethereum address as filename

---

## 🛠️ Dependencies

| Dependency | Version | Purpose |
|------------|---------|---------|
| **Java** | 17+ | Core language |
| **Jackson** | 2.15.2 | JSON parsing |
| **OpenCSV** | 5.8 | CSV export |
| **JUnit 5** | 5.10.0 | Testing |

---

**Built with ❤️ using Java 17, Maven, and Clean Architecture principles**
