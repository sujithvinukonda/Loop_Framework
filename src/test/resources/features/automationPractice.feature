Feature: I want to test Automation testing practice page

  @name
  Scenario Outline: I want to send data into name field
    Given I successfully load the case data "<Case_ID>"
    When I click the name field
    Then I enter details

    Examples: 
      | Case_ID   |
      | TC01_Name |
      | TC02_Name |
  