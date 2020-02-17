package com.cf.provider.mybatis.generator;

import com.cf.provider.HumpLineUtil;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @program: dfcloud-policy->LombokPlugin
 * @description: generator 生成文件带lombok
 * @author: caofeng
 * @create: 2019-12-31 13:48
 **/
public class LombokPlugin extends PluginAdapter {
    private Set<String> mappers = new HashSet();
    private boolean caseSensitive = false;
    private String beginningDelimiter = "";
    private String endingDelimiter = "";
    private String schema;
    private CommentGeneratorConfiguration commentCfg;
    public LombokPlugin() {
    }
    @Override
    public void setContext(Context context) {
        super.setContext(context);
        this.commentCfg = new CommentGeneratorConfiguration();
        this.commentCfg.setConfigurationType(MyCommentGenerator.class.getCanonicalName());
        context.setCommentGeneratorConfiguration(this.commentCfg);
        context.getJdbcConnectionConfiguration().addProperty("remarksReporting", "true");
    }
    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType("lombok.Data");
        topLevelClass.addImportedType("io.swagger.annotations.ApiModel");
        topLevelClass.addImportedType("io.swagger.annotations.ApiModelProperty");
        topLevelClass.addImportedType("lombok.EqualsAndHashCode");
        topLevelClass.addImportedType("org.apache.ibatis.type.Alias");
        topLevelClass.addImportedType("javax.persistence.Column");
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        topLevelClass.addAnnotation("@Data");
        topLevelClass.addAnnotation("@ApiModel(value = \"" + introspectedTable.getFullyQualifiedTable() + introspectedTable.getRemarks() + "\")");
        topLevelClass.addAnnotation("@Alias(value = \"" + HumpLineUtil.underlineToCamel(tableName) + "Entity\")");
        topLevelClass.addAnnotation("@EqualsAndHashCode(callSuper = true)");
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine("* @author CF ");
        topLevelClass.addJavaDocLine("* @date  " + this.date2Str(new Date()));
        topLevelClass.addJavaDocLine("*/");
        this.processEntityClass(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        interfaze.addJavaDocLine("/**");
        interfaze.addJavaDocLine("* Created by Mybatis Generator on " + this.date2Str(new Date()));
        interfaze.addJavaDocLine("*/");
        return true;
    }
    private void processEntityClass(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
//        topLevelClass.addImportedType("javax.persistence.*");
//        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
//        if (StringUtility.stringContainsSpace(tableName)) {
//            tableName = this.context.getBeginningDelimiter() + tableName + this.context.getEndingDelimiter();
//        }

//        if (this.caseSensitive && !topLevelClass.getType().getShortName().equals(tableName)) {
//            topLevelClass.addAnnotation("@Table(name = \"" + this.getDelimiterName(tableName) + "\")");
//        } else if (!topLevelClass.getType().getShortName().equalsIgnoreCase(tableName)) {
//            topLevelClass.addAnnotation("@Table(name = \"" + this.getDelimiterName(tableName) + "\")");
//        } else if (StringUtility.stringHasValue(this.schema) || StringUtility.stringHasValue(this.beginningDelimiter) || StringUtility.stringHasValue(this.endingDelimiter)) {
//            topLevelClass.addAnnotation("@Table(name = \"" + this.getDelimiterName(tableName) + "\")");
//        }

    }
    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }
    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }
    public String getDelimiterName(String name) {
        StringBuilder nameBuilder = new StringBuilder();
        if (StringUtility.stringHasValue(this.schema)) {
            nameBuilder.append(this.schema);
            nameBuilder.append(".");
        }

        nameBuilder.append(this.beginningDelimiter);
        nameBuilder.append(name);
        nameBuilder.append(this.endingDelimiter);
        return nameBuilder.toString();
    }
    private String date2Str(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf.format(date);
    }
}
