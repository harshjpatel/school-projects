#CS425 Project
##Setup Ember
1. Make sure you have git installed on your PC.
2. Open terminal or some git manager and clone the repo at with git clone https://github.com/harshjpatel/CS-425-project---online-school-tracking-Node-application-with-node.js/edit/master/README.md
   bitbucket has a tool to see the clone url in the **Overview** section.
3. After cloning the repo, cd into **frontend** and do a `npm install` and `bower install` or follow the README.md there.
   This installs ember and all it's dependencies. Ember can be run via the command line with `ember serve`, but for our case we will run it with
   `ember serve` for our sails backend. You can view the app via `localhost:4200`.

##Documentation
* Ember - https://guides.emberjs.com/v2.12.0/
* Ember API -https://emberjs.com/api/

##Overview
Ember is a frontend framework for making single page web applications. 

When you look into the **frontend** folder you'll see all of the Ember application. It's divided into several parts which are all located in the **app** folder:

* adapters -> Adapters are used to convert data from a server to something ember can work with.
* components -> Components are pretty much HTML. They are sort of like objects on a page. In our case, for example, our tables displayed are components.
* controllers -> Don't worry too about this.
* helpers -> Don't worry too much about this.
* models -> Entities, pretty much. Models are controlled via Ember-Data. They are an abstraction, or simplification of the entities and let us not worry about the details.
* routes -> Our pages. Routes have hierarchy which can be viewed in the `router.js` file in the root folder
* serializers -> Aids in retrieving data from server (don't worry too much about this)
* services -> Persistent code that is available throughout the application
* styles -> CSS/SASS
* templates -> HTML/Handlebars

A couple of things to note:
  * The application route is the highest most route. It can be seen via the navigation bar at the top. Routes are rendering onto it.
  * I've divided most of the routes as sub-routes of `main`, which is where they are rendered.
  * Our routes are admin, teachers, students, etc. They all should have a `general-table` component rendered.


##What We Have To Do
1. Add the `general-table` to all of the routes.
2. Setup the relationships in the Ember Models and the Sails Models and controllers
3. Add the rest of CRUD to the sails controllers (create, read, update, delete)
# CS-425-project---online-school-tracking-Node-application-with-node.js
