node {
   stage('Creating Jobs') {
      step (
         $class: 'ExecuteDslScripts',
         targets: ['jobs.groovy'].join('\n'),
         removedJobAction: 'DELETE',
         removedViewAction: 'DELETE',
         lookupStrategy: 'SEED_JOB'
      )
   }
}
