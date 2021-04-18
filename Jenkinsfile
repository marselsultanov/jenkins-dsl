node {
   stage('Creating Jobs') {
      step('Job DSL') {
         $class: 'ExecuteDslScripts',
         targets: ['jobs.groovy'].join('\n'),
         removedJobAction: 'DELETE',
         removedViewAction: 'DELETE',
         lookupStrategy: 'SEED_JOB'
      }
   }
}
