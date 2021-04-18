node {
	stage('Checking out') {
		checkout scm
	}

	stage('Creating jobs') {
		jobDsl (
			targets: 'jobs.groovy'
		)
	}
}
