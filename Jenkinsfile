node(env.SLAVE) {

   stage ('Checking out') {
      url: 'https://github.com/marselsultanov/jenkins-dsl'
   }

   stage ('Job DSL') {
      step (
         $class: 'ExecuteDslScripts',
         targets: ['jobs.groovy'].join('\n'),
         removedJobAction: 'DELETE',
         removedViewAction: 'DELETE',
         lookupStrategy: 'SEED_JOB'
      )
   }
}
