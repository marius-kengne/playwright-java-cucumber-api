# Playwright Java Cucumber API

Ce projet permet d'exécuter des **tests automatisés d'API REST** en utilisant **Java**, **Playwright**, **Cucumber**, et **JUnit**.  
Il génère également un rapport au format `cucumber.json` compatible avec **Xray (Jira)** pour l'import de résultats.

---

## 🚀 Lancer les tests

### 📦 Prérequis

- Java 17+
- Maven
- Node.js (pour utiliser Playwright)
- Playwright installé via la commande suivante :

```bash
npx playwright install
```

---

### ▶️ Exécution via Maven

```bash
mvn clean test
```

---

### 📊 Rapport généré

- `target/cucumber-report/cucumber.json` (pour Xray)
- `target/cucumber-report.html` (rapport lisible)

---

## 🧰 Outils utilisés

- 🕸️ **Playwright**
- 🍃 **Cucumber (Gherkin)**
- ☕ **Java**
- 🧪 **JUnit**
- 🧬 **Xray** (pour l'intégration avec Jira)
