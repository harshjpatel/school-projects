/* eslint-env node */
const EmberApp = require('ember-cli/lib/broccoli/ember-app');

module.exports = function(defaults) {
  var app = new EmberApp(defaults, {
    'ember-bootstrap': {
      'bootstrapVersion': 3,
      'importBootstrapFont': true,
      'importBootstrapCSS': true
    },
    'ember-cli-babel': {
      includePolyfill: true
    },
    svg: {
      paths: [
        'public/assets/images/svgs'
      ]
    },
    sassOptions: {
      includePaths: [ 'node_modules/ember-dialog/addon/styles' ]
    }
  });
  app.import(app.bowerDirectory + '/roboto-fontface/fonts/roboto/Roboto-Thin.woff2', { destDir: 'fonts/roboto' });
  app.import(app.bowerDirectory + '/roboto-fontface/fonts/roboto/Roboto-Thin.woff', { destDir: 'fonts/roboto' });
  app.import(app.bowerDirectory + '/roboto-fontface/fonts/roboto/Roboto-Thin.ttf', { destDir: 'fonts/roboto' });
  app.import(app.bowerDirectory + '/roboto-fontface/fonts/roboto/Roboto-Thin.eot', { destDir: 'fonts/roboto' });

  app.import(app.bowerDirectory + '/roboto-fontface/fonts/roboto/Roboto-Light.woff2', { destDir: 'fonts/roboto' });
  app.import(app.bowerDirectory + '/roboto-fontface/fonts/roboto/Roboto-Light.woff', { destDir: 'fonts/roboto' });
  app.import(app.bowerDirectory + '/roboto-fontface/fonts/roboto/Roboto-Light.ttf', { destDir: 'fonts/roboto' });
  app.import(app.bowerDirectory + '/roboto-fontface/fonts/roboto/Roboto-Light.eot', { destDir: 'fonts/roboto' });

  app.import(app.bowerDirectory + '/roboto-fontface/fonts/roboto/Roboto-Regular.woff2', { destDir: 'fonts/roboto' });
  app.import(app.bowerDirectory + '/roboto-fontface/fonts/roboto/Roboto-Regular.woff', { destDir: 'fonts/roboto' });
  app.import(app.bowerDirectory + '/roboto-fontface/fonts/roboto/Roboto-Regular.ttf', { destDir: 'fonts/roboto' });
  app.import(app.bowerDirectory + '/roboto-fontface/fonts/roboto/Roboto-Regular.eot', { destDir: 'fonts/roboto' });

  app.import(app.bowerDirectory + '/roboto-fontface/fonts/roboto/Roboto-Medium.woff2', { destDir: 'fonts/roboto' });
  app.import(app.bowerDirectory + '/roboto-fontface/fonts/roboto/Roboto-Medium.woff', { destDir: 'fonts/roboto' });
  app.import(app.bowerDirectory + '/roboto-fontface/fonts/roboto/Roboto-Medium.ttf', { destDir: 'fonts/roboto' });
  app.import(app.bowerDirectory + '/roboto-fontface/fonts/roboto/Roboto-Medium.eot', { destDir: 'fonts/roboto' });

  app.import(app.bowerDirectory + '/roboto-fontface/fonts/roboto/Roboto-Bold.woff2', { destDir: 'fonts/roboto' });
  app.import(app.bowerDirectory + '/roboto-fontface/fonts/roboto/Roboto-Bold.woff', { destDir: 'fonts/roboto' });
  app.import(app.bowerDirectory + '/roboto-fontface/fonts/roboto/Roboto-Bold.ttf', { destDir: 'fonts/roboto' });
  app.import(app.bowerDirectory + '/roboto-fontface/fonts/roboto/Roboto-Bold.eot', { destDir: 'fonts/roboto' });
  app.import(app.bowerDirectory + '/moment/moment.js');
  // Use `app.import` to add additional libraries to the generated
  // output files.
  //
  // If you need to use different assets in different
  // environments, specify an object as the first parameter. That
  // object's keys should be the environment name and the values
  // should be the asset to use in that environment.
  //
  // If the library that you are including contains AMD or ES6
  // modules that you would like to import into your application
  // please specify an object with the list of modules as keys
  // along with the exports of each module as its value.

  return app.toTree();
};
