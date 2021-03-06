/**
 * Copyright (c) 2014-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.litho.sections.processor;

import com.facebook.litho.specmodels.model.ClassNames;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Modifier;

/**
 * Generates getTreePropsForChildren methods.
 */
class GetTreePropsForChildrenMethodBuilder {

  static class CreateTreePropMethodData {
    String name;
    TypeName returnType;
    List<Parameter> parameters = new ArrayList<>();
  }

  List<CreateTreePropMethodData> createTreePropMethods = new ArrayList<>();
  String lifecycleImplClass;
  String delegateName;
  ClassName contextClassName;
  ClassName componentClassName;

  MethodSpec build() {
    final MethodSpec.Builder builder = MethodSpec.methodBuilder("getTreePropsForChildren")
        .addAnnotation(Override.class)
        .addModifiers(Modifier.PROTECTED)
        .returns(ClassNames.TREE_PROPS)
        .addParameter(contextClassName, "c")
        .addParameter(componentClassName, "_abstractImpl")
        .addParameter(ClassNames.TREE_PROPS, "parentTreeProps")
        .addStatement(
            "final $L $L = ($L) $L",
            lifecycleImplClass,
            "_impl",
            lifecycleImplClass,
            "_abstractImpl")
        .addStatement(
            "final $T childTreeProps = $T.copy(parentTreeProps)",
            ClassNames.TREE_PROPS,
            ClassNames.TREE_PROPS);

    for (CreateTreePropMethodData method : createTreePropMethods) {
      CodeBlock.Builder block = CodeBlock.builder();
      block
          .add(
          "childTreeProps.put($L.class, $L.$L(\n",
          method.returnType,
          delegateName,
          method.name)
          .indent()
          .indent();
      List<Parameter> parameters = method.parameters;

      for (int i = 0; i < parameters.size(); i++) {
        if (i == 0) {
          block.add("($T) $L", contextClassName, "c");
        } else {
          block.add("($T) _impl.$L", parameters.get(i).type, parameters.get(i).name);
        }
        if (i < parameters.size() - 1) {
          block.add(",\n");
        }
      }

      builder.addCode(block.add("));\n")
          .unindent()
          .unindent()
          .build());
    }

    return builder
        .addStatement("return childTreeProps")
        .build();
  }
}
