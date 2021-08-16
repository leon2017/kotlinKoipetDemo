package com.lightinthebox.api.compile

import com.google.auto.service.AutoService
import com.lightinthebox.api.annotations.LitbServiceBind
import com.squareup.kotlinpoet.*
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(LitbServiceAnnotationProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class LitbServiceAnnotationProcessor: AbstractProcessor() {

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(LitbServiceBind::class.java.name)
    }

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latest()

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment
    ): Boolean {
        roundEnv.getElementsAnnotatedWith(LitbServiceBind::class.java)
            .forEach {
                if (it.kind != ElementKind.CLASS) {
                    processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "Only classes can be annotated")
                    return true
                }
                processAnnotation(it)
            }
        return false
    }


    private fun processAnnotation(element: Element) {
        val annotation = element.getAnnotation(LitbServiceBind::class.java)
        val serviceName = annotation.serviceName
        val className = element.simpleName.toString()
        var fileName = "${className}Request"
        if (serviceName.isNotEmpty()){
            fileName = "${serviceName}Request"
        }
        val pack = processingEnv.elementUtils.getPackageOf(element).toString()
        val fileBuilder = FileSpec.builder(pack,fileName)
        val classBuilder = TypeSpec.classBuilder(fileName)
//        for (enclosed in element.enclosedElements) {
//            if (enclosed.kind == ElementKind.FIELD) {
//                classBuilder.addProperty(
//                    PropertySpec.builder(enclosed.simpleName.toString(), enclosed.asType().asTypeName(), KModifier.PRIVATE)
//                        .initializer("null")
//                        .build()
//                )
//                classBuilder.addFunction(
//                    FunSpec.builder("get${enclosed.simpleName}")
//                        .returns(enclosed.asType().asTypeName())
//                        .addStatement("return ${enclosed.simpleName}")
//                        .build()
//                )
//                classBuilder.addFunction(
//                    FunSpec.builder("set${enclosed.simpleName}")
//                        .addParameter(ParameterSpec.builder("${enclosed.simpleName}", enclosed.asType().asTypeName()).build())
//                        .addStatement("this.${enclosed.simpleName} = ${enclosed.simpleName}")
//                        .build()
//                )
//            }
//        }
        val file = fileBuilder.addType(classBuilder.build()).build()
        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        file.writeTo(File(kaptKotlinGeneratedDir))
    }
}