Feature: Tester l'API Quotes

  Scenario: Vérifier une citation existante
    Given je prépare un client HTTP Playwright
    When j'envoie une requête GET sur "http://localhost:5000/quotes/1"
    Then le code réponse doit être 200

  Scenario: Ajouter une citation et vérifier la réponse
    Given je prépare un client HTTP Playwright
    When j'envoie une requête POST sur "http://localhost:5000/quotes" avec le corps "src/test/resources/data/quote1.json"
    Then le code réponse doit être 200