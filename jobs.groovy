def giturl = 'https://github.com/MNT-Lab/d323dsl'
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
	job("Child$i") {
		scm {
			git {
			remote {
				url(giturl)
			}
			branch('$BRANCH_NAME')
			}
		}
	
        parameters {
            activeChoiceParam('Branch') {
                choiceType('SINGLE_SELECT')
                groovyScript {
                    script('''("git ls-remote -h https://github.com/marselsultanov/jenkins-dsl").execute().text.readLines().collect{
					it.split()[1].replaceAll(\'refs/heads/\', \'\')}.sort()''')
                }
            }
        }

        steps {
            shell('''bash script.sh > output.txt
			tar -czf $Branch_dsl_script.tar.gz jobs.groovy''')
        }
		
        publishers {
            archiveArtifacts {
                pattern('$Branch_dsl_script.tar.gz')
                allowEmpty(false)
                onlyIfSuccessful(false)
                fingerprint(false)
                defaultExcludes(true)
            }
        }
    }
}
