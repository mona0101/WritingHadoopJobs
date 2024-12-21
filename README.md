# 🚀 Hadoop Jobs

This repository contains datasets and code for implementing two MapReduce jobs in Hadoop. The files and steps included demonstrate the process of creating datasets and writing Hadoop jobs to perform specific analyses on the data.

---
## 🔧 Steps Performed
### 📂 Dataset Creation
- The datasets (Buyers and Purchases) were generated using Python and the **Faker** library.  
- The Python code for creating the datasets can be found in the `synthetic_dataset.ipynb` file.
- Details:  
  - **Buyers Dataset**: Contains details about individual buyers.  
  - **Purchases Dataset**: Contains details about each purchase, including buyer ID and purchase details.  

### 📊 Writing MapReduce Jobs
- **1️⃣ First Job** (Map-only):  
  Filters and reports buyers whose age is between 20 and 50.  

- **2️⃣ Second Job** (MapReduce):  
  Reports, for every buyer, the number of purchases they made and the total sum of the prices of these purchases.  

---

## 📁 Files in the Repository
- **📄 Buyers.csv**: Dataset for buyers (generated using `synthetic_dataset.ipynb`).  
- **📄 Purchases.csv**: Dataset for purchases (generated using `synthetic_dataset.ipynb`).  
- **🖥️ BuyersAge.java**: Java code for the first MapReduce job (filtering buyers by age).  
- **🖥️ Purchases.java**: Java code for the second MapReduce job (counting purchases and summing prices for each buyer).  
- **📦 job1-0-1-SNAPSHOT.jar**: Compiled JAR file for the first MapReduce job.  
- **📦 job2-0-1-SNAPSHOT.jar**: Compiled JAR file for the second MapReduce job.  
- **📒 synthetic_dataset.ipynb**: Notebook used for generating synthetic datasets with the Faker library.  

---

Feel free to explore the files and use the code in your own Hadoop projects! 😊
