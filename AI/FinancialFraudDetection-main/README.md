# ITMD524
# Anti-Money Laundering Detection with Machine Learning

This repository explores various machine learning techniques to detect suspicious transactions using the [IBM Anti-Money Laundering dataset](https://www.kaggle.com/datasets/ealtman2019/ibm-transactions-for-anti-money-laundering-aml). With an estimated $2 trillion laundered globally each year, the aim is to improve detection precision and reduce false positives using supervised, unsupervised, graph-based, and time-series models.

## Team Members

- **Sophia Robles** – srobles2@hawk.iit.edu  
- **Nicholas Simpkins** – nsimpkins@hawk.iit.edu  
- **Harsh Patel** – hpatel100@hawk.iit.edu  

## Dataset Overview

- **Source**: IBM AML Dataset (Kaggle)
- **Size**: ~13 million transactions, 650MB
- **Features**: Transaction metadata including bank codes, amounts, currencies, account info, and timestamps
- **Target**: `isLaundering` (binary classification label)

## Research Goals

- **Classification**: Predict whether a transaction is laundering-related
- **Anomaly Detection**: Flag novel suspicious activity without labels
- **Graph Analysis**: Identify networks or clusters of colluding accounts
- **Time-Series Modeling**: Detect abnormal transaction behavior over time

## Project Structure

