mvn -f ../ -T 1C clean install -DskipTests
build_status=$?

if [ $build_status = 0 ]; then
  echo 'Maven build completed, building docker images...'

  services_directories=`find ../services -mindepth 1 -maxdepth 1 -type d -exec readlink -f {} \;`

  for service_directory in $services_directories ; do
      jar_file=`find $service_directory/target -name '*.jar'`
      jar_filename=`basename $jar_file`

      build_context=$service_directory/target/
      cp ./Dockerfile $build_context

      service_name=`basename $service_directory`
      image_name=omnika/$service_name
      echo 'Building image' $image_name
      docker build --build-arg jar_file=$jar_filename -t $image_name:latest $build_context

      if [ $? != 0 ]; then
          exit 1
      fi
  done

  echo 'Finished successfully';
else
  echo 'Maven build failure'
fi