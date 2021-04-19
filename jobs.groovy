job ("Main") {
  parameters {
    choiceParam('Branch', ['main', 'msultanov'])
    activeChoiceParam('Child') {
      choiceType('CHECKBOX')
      groovyScript {
        script('return ["Child1","Child2","Child3","Child4"]')
      }
    }
  }

  steps {
    downstreamParameterized {
      trigger('$Child') {
        block {
          buildStepFailure('FAILURE')
          failure('FAILURE')
          unstable('UNSTABLE')
        }
        parameters {
          predefinedProp('Branch', '$Branch')
        }
      }
    }
  }
}

for (i in (1..4)) {
job("Child$i") {
  parameters {
    activeChoiceParam('Branch') {
      choiceType('SINGLE_SELECT')
      groovyScript {
        script('''("git ls-remote -h https://github.com/marselsultanov/jenkins-dsl").execute().text.readLines().collect{
it.split()[1].replaceAll(\'refs/heads/\', \'\')}.sort()''')
      }
    }
  }

  scm {
    git {
    remote {
      url('https://github.com/marselsultanov/jenkins-dsl')
  }
    branch('$Branch')
    }
  }

  steps {
    shell('''bash script.sh > output.txt
tar -czf ${Branch}_dsl_script.tar.gz jobs.groovy''')
  }

  publishers {
    archiveArtifacts {
      pattern('${Branch}_dsl_script.tar.gz')
      allowEmpty(false)
      onlyIfSuccessful(false)
      fingerprint(false)
      defaultExcludes(true)
    }
  }
}
}
