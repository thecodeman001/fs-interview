# Code assessment

> [!IMPORTANT]  
> **Be sure to create yourself a working copy using this repo as a template**.
> [Here is how to do it](https://github.com/synapse-medicine/fs-interview/wiki/How-to-start/). 

## Provided material

* A Java 21 Spring backend with a populated SQLite in memory database and endpoints to:
    * get a Patient by id
    * get the list of allergies for a given Patient
    * update a Patient's allergies
* A Node 20 React application with the following pages:
    * a home page that lists all patients
    * a page that displays the patient's information
    * an error page
* A Dockerfile and a Docker compose
* A basic CI using GitHub Actions

## Instructions

### Before you start 

The provided repository needs to be used as a *code template* to create a repository in your personal space. 


### Adding a new feature

Your first task will be to develop a new feature.
The goal is to add a search molecule component to the patient profile page.
This component will show a list of molecules that match the input text.
When the user selects a molecule from this list, it should be added to the patient allergies using the existing endpoint.

This gives the following user workflow:
1. The user clicks on the search field and search for a molecule name using free text.
2. The user selects the molecule from a list of results that match the search text.
3. The user chooses the molecule by clicking on it.
4. The chosen molecule is added to the patient’s allergy list.

You are free to develop the feature as you want. The UX and the design are taken into account, but you should not spend too much
time on it.

### Updating the CI

Your second task is to change the current GitHub Actions CI to build the Docker images provided in the repository.

### Going further

Your last task is to be critical :smile:.

The provided code is not representative of the expectations we have for production code (on purpose).
We instead encourage you to build the feature in a way that you think is the best.
We **don't** expect you to fix the current code though, just to add the new feature.

List what you would improve in the codebase (current or in your new feature) in the `Improvements section`.

## Improvements:

### Frontend
>  Separated backend logic for fetching patient data into a separate service

## Possible Improvements

### Backend
* **Error Handling**:
  - Improve error messages for specific cases, such as invalid patient or molecule IDs.
  - Add detailed validation responses for malformed requests.

### Frontend
* **Search Input Optimization**:
  - Implement debouncing for the search input to reduce API calls while typing.
  
* **UI Enhancements**:
  - Add a loading spinner during the molecule search and consider adding pagination or infinite scrolling for large datasets.

* **Testing**:
  - Expand unit tests and add integration tests to ensure that the new molecule search feature interacts seamlessly with the existing codebase.
  
* **State Management Refactor**:
  - Consider moving the patient allergies state to a global store (e.g., Redux) to better manage state across components, improving scalability as more features are added.

* ...
