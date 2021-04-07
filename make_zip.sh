#!/bin/bash

realpath() {
  cd $1
  pwd
}

if [[ $# -lt 2 ]]; then
  printf "usage: ./clean_examples.sh <input dir> <output.zip>"
  exit 1
fi

dir_in=$(realpath $1)
zip_fn=$(basename $2)
temp_dir=$(mktemp -d)
dir_out="${temp_dir}/${zip_fn%.*}"
mkdir -p "$dir_out"
zip_out=$(realpath $(dirname $2))/"$zip_fn"

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

cd ..
zip -9r "$zip_out" $(basename "$dir_out")

rm -rf "${temp_dir}"
