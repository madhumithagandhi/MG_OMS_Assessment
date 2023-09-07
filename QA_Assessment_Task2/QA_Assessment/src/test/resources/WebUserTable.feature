Feature: Login functionality
  @WebTables
  Scenario Outline: Web Tables
    Given Opens Browser
    Given Launchs URL
    And Checks if in User List Table Page
    When Hits Add button
    When Enters user details including "<firstName>" and "<lastName>"
    And Hits save button
    Then Details should appear on the table
    Examples:
    | firstName | lastName |
    | WebTbls | User1 |
    | WebTbls | User2 |
    | WebTbls | User3 |

