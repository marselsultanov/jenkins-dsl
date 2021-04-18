node {
   stage('Checking out') {
      checkout scm
   }

   stage('Creating jobs') {
      step (
         $class: 'ExecuteDslScripts',
         targets: ['jobs.groovy'].join('\n'),
         removedJobAction: 'DELETE',
         removedViewAction: 'DELETE',
         lookupStrategy: 'SEED_JOB'
      )
   }
}
