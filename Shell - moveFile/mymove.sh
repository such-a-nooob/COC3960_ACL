#! bin/bash

# check for source folder
if [ ! -d ${1} ]
then
    echo "ERROR : Source folder not found"
    exit
fi

# check for destination folder
if [ ! -d ${2} ]
then
    echo "ERROR : Destination folder not found"
    exit
fi

# if further arguments are not given
if [ ! $# -ge 3 ]
then
    for file in $1*
    do
        # move ALL FILES from source directory to destination directory
        if [ -f "$file" ]
        then
            mv $file $2
        fi
    done
    echo "All the files in $1 are moved to $2"
fi

# declare a valid month list
MONTHS=("Jan" "Feb" "Mar" "Apr" "May" "Jun" "Jul" "Aug" "Sep" "Oct" "Nov" "Dec")

# if the third argument passed is -M
if [ "$3" = "-M" ]
then
    # check for incorrect month in the month list 
    for m in "${@:4}"
    do
        if [[ ! "${MONTHS[@]}" =~ "${m}" ]]
        then
            echo "ERROR : Incorrect month -" ${m}
            exit
        fi
    done

    for file in $1*
    do
        if [ -f "$file" ] 
        then
            # find the month in which the file is created
            birthMonth=$(ls -l "$file" | awk '{print $6}')

            for month in "${@:4}"
            do
                # move all the files in the source directory CREATED IN THE GIVEN MONTHS to the destination directory
                if [ "$birthMonth" = "$month" ]
                then
                    mv $file $2
                fi
            done
        fi
    done
    echo "Files created in - ${@:4} are moved to $2"
fi

