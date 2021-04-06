#!/bin/bash

realpath() {
  cd $1
  pwd
}

if [[ $# -lt 2 ]]; then
  printf "usage: ./clean_examples.sh <input dir> <output dir> [output.zip]"
  exit 1
fi

dir_in=$(realpath $1)
dir_out=$2
if [[ $# -eq 3 ]]; then
  zip_out=$(realpath $(dirname $3))/$(basename $3)
fi

if [[ "$dir_in" == "$dir_out" ]]; then
  printf "error: input and output dir must be different"
  exit 1
fi

mkdir -p "$dir_out"
cd "$dir_out"
dir_out=$(pwd)

for pom_file in $(cd "$dir_in"; find . -name "pom.xml"); do
  this_subprj_dir_rel=$(dirname "$pom_file")
  src_dir_abs="$dir_in/$this_subprj_dir_rel"
  mkdir -p "$this_subprj_dir_rel"
  pushd "$dir_out" > /dev/null
  cd "$this_subprj_dir_rel"
  cp "$src_dir_abs/pom.xml" .
  cp -r "$src_dir_abs/src" .
  rm -r $(find . -type f -name ".*") &> /dev/null
  popd > /dev/null
done

if [[ -e "$dir_in/README.md" ]]; then
  pandoc \
    -V geometry:margin=2.5cm \
    -V documentclass='scrartcl' \
    -V fontfamily='times' \
    -V fontsize='12pt' \
    --pdf-engine=pdflatex \
    "$dir_in/README.md" -o "$dir_out/README.pdf"
fi

if [[ $# -eq 3 ]]; then
  cd ..
  zip -9r "$zip_out" $(basename "$dir_out")
fi
