#! /bin/bash

set -e

if [ -z "$EVENTUATE_API_KEY_ID" -o -z "$EVENTUATE_API_KEY_SECRET" ] ; then
  echo You must set EVENTUATE_API_KEY_ID and  EVENTUATE_API_KEY_SECRET
  exit -1
fi

if [ -z "$AWS_ACCESS_KEY_ID" ] ; then
  echo You must set AWS_ACCESS_KEY_ID to be able to run serverless deploy
  exit -1
fi
if [ -z "$AWS_SECRET_ACCESS_KEY" ] ; then
  echo You must set AWS_SECRET_ACCESS_KEY to be able to run serverless deploy
  exit -1
fi
if [ -z "$AWS_REGION" ] ; then
  echo You must set AWS_REGION to be able to run serverless deploy
  exit -1
fi

./gradlew clean test buildZip -x :e2etest:test

serverless -r $AWS_REGION deploy

export AWS_GATEWAY_API_INVOKE_URL=$(serverless -r $AWS_REGION info -v | grep ServiceEndpoint | cut -d' ' -f2)
echo set export AWS_GATEWAY_API_INVOKE_URL=$AWS_GATEWAY_API_INVOKE_URL

./gradlew -P ignoreE2EFailures=false :e2etest:cleanTest :e2etest:test

serverless -r $AWS_REGION remove
