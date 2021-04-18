node {
   stage('Job DSL') {
      step (
         $class: 'ExecuteDslScripts',
         targets: ['jobs.groovy'].join('\n'),
         removedJobAction: 'DELETE',
         removedViewAction: 'DELETE',
         lookupStrategy: 'SEED_JOB'
      )
   }
}
