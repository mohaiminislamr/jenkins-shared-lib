def call() {
    echo "This is deploying the code"

    // Stop and remove existing containers (if any)
    sh '''
        docker rm -f notes-app || true
        docker rm -f db_cont || true
    '''

    // Create the Docker network if it doesn't exist
    sh '''
        docker network inspect notes-app-nw >/dev/null 2>&1 || \
        docker network create notes-app-nw
    '''

    // Start MySQL container first
    sh '''
        docker run -d -p 3306:3306 \
            --name db_cont \
            -e MYSQL_ROOT_PASSWORD=root \
            -e MYSQL_DATABASE=test_db \
            --network=notes-app-nw \
            mysql:latest
    '''

    // Wait for MySQL to be ready (optional but recommended)
    sh '''
        echo "Waiting for MySQL to initialize..."
        sleep 20
    '''

    // Start Django app
    sh '''
        docker run -d -p 8000:8000 \
            --name notes-app \
            -e DB_HOST=db_cont \
            -e DB_PORT=3306 \
            -e DB_NAME=test_db \
            -e DB_USER=root \
            -e DB_PASSWORD=root \
            --network=notes-app-nw \
            notes-app:latest
    '''
}
