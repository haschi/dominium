
Feature: Kassenbon erhalten
  Als Hausmann möchte ich einen Kassenbon verbuchen, damit ich mein aktuelles Vermögen kenne

  Scenario: Kassenbon verbuchen
    Given mein Vermögen beträgt 100 €
    When ich einen Kassenbon mit 30 € verbuche
    Then besitze ich ein Vermögen in Höhe von 70 €
