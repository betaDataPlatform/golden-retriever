// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: metricValue.proto

package data.platform.common.protobuf;

public final class MetricProto {
  private MetricProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_MetricValueProto_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_MetricValueProto_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_MetricValueProto_TagsEntry_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_MetricValueProto_TagsEntry_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\021metricValue.proto\"\251\001\n\020MetricValueProto" +
      "\022\016\n\006metric\030\001 \001(\t\022)\n\004tags\030\002 \003(\0132\033.MetricV" +
      "alueProto.TagsEntry\022\021\n\teventTime\030\003 \001(\003\022\r" +
      "\n\005value\030\004 \001(\001\022\013\n\003ttl\030\005 \001(\003\032+\n\tTagsEntry\022" +
      "\013\n\003key\030\001 \001(\t\022\r\n\005value\030\002 \001(\t:\0028\001B.\n\035data." +
      "platform.common.protobufB\013MetricProtoP\001b" +
      "\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_MetricValueProto_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_MetricValueProto_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_MetricValueProto_descriptor,
        new String[] { "Metric", "Tags", "EventTime", "Value", "Ttl", });
    internal_static_MetricValueProto_TagsEntry_descriptor =
      internal_static_MetricValueProto_descriptor.getNestedTypes().get(0);
    internal_static_MetricValueProto_TagsEntry_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_MetricValueProto_TagsEntry_descriptor,
        new String[] { "Key", "Value", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
