# Android Application 
It is a complete application. Each branch includes a different assignment where the final project is located in the master branch. In this application, users are registered, there is a database that stores these details of each user. When they log in, they will be able to choose a dish from a list of Japanese dishes so that they can see the specific recipe.

As part of the project, we were asked to create two layouts (Sign in and Create account) based on the design in Figma, including fields such as name, email, phone number, and password, using Material Design Components. Each layout also includes a button (Register now and Login now) to switch between the corresponding screens (activities).
Next, CredentialsManager was implemented, as well as tests for the validity of the email and password entered by the user. When the user tries to log in, an error message appears if the password or email is invalid (e.g., the error appears when the email does not contain @gmail.com or the password is weak- I have set a specific pattern).
In addition, when adding a new account, a check is performed to see if the email has already been used. If so, a relevant error is returned. Then, the Login and Register activities were converted to fragments. Registered user data is stored in a database.
Next, a RecyclerView list was created with predefined recipes (with image, title, and short description), and a recipe search feature was added using ViewModel and StateFlow. This way, only relevant recipes are displayed during the search. A loading bar lasting 2-3 seconds was also added before the list appears. Thus, when the user logs in, they can select a recipe from a list of Japanese dishes and view its contents.
Finally, we had to create an additional screen that would accept new recipe entries and display them in the main recipe list.



