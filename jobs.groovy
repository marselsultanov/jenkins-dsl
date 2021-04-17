def github = 'https://github.com/marselsultanov/jenkins-dsl'
job ("Main") {
    parameters {
        choiceParam('Branch', ['main', 'msultanov'])
        activeChoiceParam('Child') {
            choiceType('CHECKBOX')
            groovyScript {
                script('return ["Child 1","Child 2","Child 3","Child 4"]')
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
    job("MNTLAB-ukuchynski-child${i}-build-job") {

        scm {
            git {
                remote {
                    url(github)
                }
                branch('$BRANCH_NAME')
            }
        }

        parameters {
            activeChoiceParam('BRANCH_NAME') {
                description('Choose branch')
                choiceType('SINGLE_SELECT')
                groovyScript {
                    script('''("git ls-remote -h https://github.com/MNT-Lab/d323dsl").execute().text.readLines().collect {
                      it.split()[1].replaceAll(\'refs/heads/\', \'\')}.sort()''')
                }
            }
        }

        steps {
            shell('''
   bash script.sh > output.txt
   tar -czf ${BRANCH_NAME}_dsl_script.tar.gz jobs.groovy''')
        }
        publishers {
            archiveArtifacts {
                pattern('${BRANCH_NAME}_dsl_script.tar.gz')
                allowEmpty(false)
                onlyIfSuccessful(false)
                fingerprint(false)
                defaultExcludes(true)
            }
        }
    }
}
