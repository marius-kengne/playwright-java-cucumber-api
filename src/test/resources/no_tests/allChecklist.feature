Feature: Test API

  @TEST_AA-8
  Scenario: Création d'une ressource (POST)
    Given je prépare un client HTTP Playwright
    When j'envoie une requête POST sur "http://localhost:5000/quotes" avec le body:
		      """
		      {
		        "id": 201,
		        "author": "Albert Einstein",
		        "quote": "Life is like riding a bicycle. To keep your balance you must keep moving."
		      }
		      """
    Then le code réponse doit être 200

  @TEST_AA-9
  Scenario: Validation de la réponse JSON
    Given je prépare un client HTTP Playwright
    When j'envoie une requête GET sur "http://localhost:5000/quotes/1"
    Then le code réponse doit être 200
    And la réponse doit contenir "author"
    And la réponse doit contenir "quote"

  @TEST_AA-10
  Scenario Outline: Jeux de données multiples (Scenario Outline)
    Given je prépare un client HTTP Playwright
    When j'envoie une requête POST sur "http://localhost:5000/quotes" avec l'id "<id>", l'auteur "<author>", et la citation "<quote>"
    Then le code réponse doit être 200

    Examples:
      | id  | author         | quote                                     |
      | 301 | Nelson Mandela | The greatest glory in living lies not... |
      | 302 | Maya Angelou   | You will face many defeats in life...    |
      | 303 | Steve Jobs     | Stay hungry, stay foolish.               |

  @TEST_AA-11
  Scenario: Chaînage de requêtes POST -> GET
    Given je prépare un client HTTP Playwright
    And j'envoie une requête POST sur "http://localhost:5000/quotes" avec le body:
		      """
		      {
		        "author": "Ada Lovelace",
		        "quote": "The Analytical Engine does not reason."
		      }
		      """
		    ##Then le code réponse doit être 200
    When j'envoie une requête GET sur "http://localhost:5000/quotes/1"
    Then le code réponse doit être 200
    And la réponse doit contenir "author"

  @TEST_AA-12
  Scenario: Test avec authentification Bearer
    Given je prépare un client HTTP Playwright avec le token "ey123.fake.token"
    When j'envoie une requête GET sur "http://localhost:5000"
    Then le code réponse doit être 200

  @TEST_AA-13
  Scenario: Gestion d'erreur 404 attendue
    Given je prépare un client HTTP Playwright
    When j'envoie une requête GET sur "http://localhost:5000/quotes/9999"
    Then le code réponse doit être 404

  @TEST_AA-14
  Scenario: Réponse avec champ dynamique (id généré automatiquement)
    Given je prépare un client HTTP Playwright
    When j'envoie une requête POST sur "http://localhost:5000/quotes" avec le body:
		      """
		      {
		        "author": "Grace Hopper",
		        "quote": "The most dangerous phrase is 'we’ve always done it this way.'"
		      }
		      """
    Then le code réponse doit être 200
    And la réponse doit contenir "id"

  @TEST_AA-15
  Scenario: Utilisation de fichier JSON comme body
    Given je prépare un client HTTP Playwright
    And je lis le fichier JSON "src/test/resources/data/payload.json"
    When j'envoie une requête POST sur "http://localhost:5000/quotes" avec ce payload
    Then le code réponse doit être 200

