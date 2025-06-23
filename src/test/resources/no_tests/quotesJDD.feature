Feature: Ajouter plusieurs citations via des jeux de données

  Scenario Outline: Création d'une citation avec des données variables
    Given je prépare un client HTTP Playwright
    When j'envoie une requête POST sur "http://localhost:5000/quotes" avec l'id "<id>", l'auteur "<author>", et la citation "<quote>"
    Then le code réponse doit être 200

    Examples:
      | id  | author         | quote                                     |
      | 101 | Nelson Mandela | The greatest glory in living lies not...  |
      | 102 | Maya Angelou   | You will face many defeats in life...     |
      | 103 | Steve Jobs     | Stay hungry, stay foolish.                |
