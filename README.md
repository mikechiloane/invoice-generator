# Invoice Generator

A Java-based PDF invoice generator built with Apache PDFBox that creates professional invoices with customizable sections.

## 📋 Table of Contents

- [Features](#-features)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Usage](#-usage)
- [Project Structure](#-project-structure)
- [API Reference](#-api-reference)
- [Configuration](#-configuration)
- [Testing](#-testing)
- [Contributing](#-contributing)
- [License](#-license)

## ✨ Features

- **PDF Invoice Generation**: Creates professional PDF invoices using Apache PDFBox
- **Modular Design**: Sectioned approach with separate components for different invoice parts
- **Custom Fonts**: Supports custom Futura fonts for professional typography
- **Flexible Data Input**: Accepts customer information, invoice items, and billing details
- **Invoice Sections**:
  - Header with company branding
  - Customer information and billing address
  - Itemized table with descriptions, quantities, and pricing
  - Payment information and terms
  - Tax calculations and totals

## 🔧 Prerequisites

- **Java 17** or higher
- **Maven 3.6+** for dependency management and building
- **Apache PDFBox 3.0.5** (managed via Maven)

## Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/mikechiloane/invoice-generator.git
   cd invoice-generator
   ```

2. **Build the project:**
   ```bash
   mvn clean compile
   ```

3. **Run tests:**
   ```bash
   mvn test
   ```

4. **Package the application:**
   ```bash
   mvn package
   ```

##  Usage

### Basic Usage

```java
import com.recceda.invoice.api.InvoiceGenerator;
import com.recceda.invoice.common.CustomerInvoiceData;
import com.recceda.invoice.common.InvoiceItem;

// Create invoice items
InvoiceItem[] items = {
    new InvoiceItem("Product A", 2, 75.0, "High quality product", "Electronics"),
    new InvoiceItem("Service B", 1, 150.0, "Premium service", "Services")
};

// Create customer data
CustomerInvoiceData customerData = new CustomerInvoiceData(
    "John Doe",                           // Customer name
    new String[]{"123 Main St", "City, State 12345"}, // Address lines
    items,                                // Invoice items
    "2025-06-26",                        // Invoice date
    "2025-07-26",                        // Due date
    "300.00",                            // Subtotal
    "24.00",                             // Tax amount
    "8%",                                // Tax rate
    "324.00"                             // Total amount
);

// Generate invoice
InvoiceGenerator generator = new InvoiceGenerator();
generator.generateInvoice(customerData, "output/invoice.pdf");
```

### Running from Command Line

After building the project, you can run it using:

```bash
java -cp target/invoice-generator-1.0.0.jar com.recceda.invoice.api.InvoiceGenerator
```

## 📁 Project Structure

```
invoice-generator/
├── src/
│   ├── main/
│   │   ├── java/com/recceda/invoice/
│   │   │   ├── api/
│   │   │   │   └── InvoiceGenerator.java       # Main API class
│   │   │   ├── common/
│   │   │   │   ├── CustomerInvoiceData.java    # Data model for customer info
│   │   │   │   ├── InvoiceItem.java           # Data model for invoice items
│   │   │   │   └── TextUtils.java             # Text utility functions
│   │   │   ├── context/
│   │   │   │   └── PdfContext.java            # PDF rendering context
│   │   │   └── impl/sections/
│   │   │       ├── HeaderSection.java         # Invoice header rendering
│   │   │       ├── PaymentInfoSection.java    # Payment details section
│   │   │       ├── PaymentTermsSection.java   # Payment terms section
│   │   │       ├── PdfSection.java           # Base section interface
│   │   │       └── TableSection.java         # Invoice items table
│   │   └── resources/
│   │       ├── futura.ttf                     # Regular font
│   │       ├── futura_bold.ttf               # Bold font
│   │       ├── logo.png                       # Company logo
│   │       ├── address                        # Company address file
│   │       ├── banking                        # Banking information
│   │       ├── contacts                       # Contact information
│   │       └── payment_terms                  # Payment terms text
│   └── test/
│       └── java/com/recceda/invoice/
│           ├── api/
│           │   └── InvoiceGeneratorTest.java  # API tests
│           └── impl/
│               └── ReccedaInvoiceTest.java   # Implementation tests
├── target/                                    # Maven build output
├── pom.xml                                   # Maven configuration
└── README.md                                 # This file
```

## 📚 API Reference

### InvoiceGenerator

The main class for generating invoices.

#### Constructor
```java
public InvoiceGenerator()
```
Initializes a new invoice generator with default A4 page size and loads custom fonts.

#### Methods

##### generateInvoice
```java
public void generateInvoice(CustomerInvoiceData customerInvoiceData, String outputPath)
```
Generates a PDF invoice and saves it to the specified path.

**Parameters:**
- `customerInvoiceData`: Customer and invoice data
- `outputPath`: File path where the PDF will be saved

### CustomerInvoiceData

Data model containing all information needed for invoice generation.

#### Constructor
```java
public CustomerInvoiceData(String customerName, String[] addressLines, 
                          InvoiceItem[] items, String invoiceDate, 
                          String invoiceDueByDate, String subTotal, 
                          String tax, String taxRate, String total)
```

### InvoiceItem

Represents a single item on the invoice.

#### Constructor
```java
public InvoiceItem(String itemName, Integer quantity, Double unitPrice, 
                   String description, String category)
```

## ⚙️ Configuration

### Font Configuration

The application uses custom Futura fonts located in `src/main/resources/`:
- `futura.ttf` - Regular font
- `futura_bold.ttf` - Bold font

### Resource Files

The following resource files can be customized:
- `address` - Company address information
- `banking` - Banking and payment details
- `contacts` - Contact information
- `payment_terms` - Payment terms and conditions
- `logo.png` - Company logo image

### PDF Settings

- **Page Size**: A4 (210 × 297 mm)
- **Font**: Custom Futura fonts
- **Layout**: Professional invoice layout with header, itemized table, and footer sections

## 🧪 Testing

The project includes comprehensive unit tests:

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=InvoiceGeneratorTest

# Generate test reports
mvn surefire-report:report
```

### Test Coverage

- **InvoiceGeneratorTest**: Tests the main API functionality
- **ReccedaInvoiceTest**: Tests specific implementation details

Test reports are generated in `target/surefire-reports/`.

## 🏗️ Building and Deployment

### Development Build
```bash
mvn clean compile
```

### Production Build
```bash
mvn clean package
```

### Running Tests
```bash
mvn test
```

### Creating Documentation
```bash
mvn javadoc:javadoc
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Style

- Follow Java naming conventions
- Use meaningful variable and method names
- Add Javadoc comments for public methods
- Maintain consistent indentation (4 spaces)

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Troubleshooting

### Common Issues

1. **Font Loading Errors**
   - Ensure font files exist in `src/main/resources/`
   - Check file permissions

2. **PDF Generation Fails**
   - Verify output directory exists and is writable
   - Check that all required data fields are provided

3. **Build Failures**
   - Ensure Java 17+ is installed
   - Run `mvn clean` before building

### Support

For issues and questions:
1. Check existing GitHub issues
2. Create a new issue with detailed description
3. Include error logs and system information

---

**Version**: 1.0.0  
**Last Updated**: June 26, 2025  
**Maintainer**: Recceda Team
