#!/bin/bash

for i in $(.travis/reports/get-reports.sh)
    do echo $AWS_URL/$ARTIFACTS_BUCKET/$TRAVIS_BUILD_NUMBER${i:1}
done