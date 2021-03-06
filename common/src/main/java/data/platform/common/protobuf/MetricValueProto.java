// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: metricValue.proto

package data.platform.common.protobuf;

/**
 * Protobuf type {@code MetricValueProto}
 */
public final class MetricValueProto extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:MetricValueProto)
    MetricValueProtoOrBuilder {
private static final long serialVersionUID = 0L;
  // Use MetricValueProto.newBuilder() to construct.
  private MetricValueProto(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private MetricValueProto() {
    metric_ = "";
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new MetricValueProto();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private MetricValueProto(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            String s = input.readStringRequireUtf8();

            metric_ = s;
            break;
          }
          case 18: {
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              tags_ = com.google.protobuf.MapField.newMapField(
                  TagsDefaultEntryHolder.defaultEntry);
              mutable_bitField0_ |= 0x00000001;
            }
            com.google.protobuf.MapEntry<String, String>
            tags__ = input.readMessage(
                TagsDefaultEntryHolder.defaultEntry.getParserForType(), extensionRegistry);
            tags_.getMutableMap().put(
                tags__.getKey(), tags__.getValue());
            break;
          }
          case 24: {

            eventTime_ = input.readInt64();
            break;
          }
          case 33: {

            value_ = input.readDouble();
            break;
          }
          case 40: {

            ttl_ = input.readInt64();
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return MetricProto.internal_static_MetricValueProto_descriptor;
  }

  @SuppressWarnings({"rawtypes"})
  @Override
  protected com.google.protobuf.MapField internalGetMapField(
      int number) {
    switch (number) {
      case 2:
        return internalGetTags();
      default:
        throw new RuntimeException(
            "Invalid map field number: " + number);
    }
  }
  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return MetricProto.internal_static_MetricValueProto_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            MetricValueProto.class, Builder.class);
  }

  public static final int METRIC_FIELD_NUMBER = 1;
  private volatile Object metric_;
  /**
   * <code>string metric = 1;</code>
   * @return The metric.
   */
  @Override
  public String getMetric() {
    Object ref = metric_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      metric_ = s;
      return s;
    }
  }
  /**
   * <code>string metric = 1;</code>
   * @return The bytes for metric.
   */
  @Override
  public com.google.protobuf.ByteString
      getMetricBytes() {
    Object ref = metric_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      metric_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int TAGS_FIELD_NUMBER = 2;
  private static final class TagsDefaultEntryHolder {
    static final com.google.protobuf.MapEntry<
        String, String> defaultEntry =
            com.google.protobuf.MapEntry
            .<String, String>newDefaultInstance(
                MetricProto.internal_static_MetricValueProto_TagsEntry_descriptor,
                com.google.protobuf.WireFormat.FieldType.STRING,
                "",
                com.google.protobuf.WireFormat.FieldType.STRING,
                "");
  }
  private com.google.protobuf.MapField<
      String, String> tags_;
  private com.google.protobuf.MapField<String, String>
  internalGetTags() {
    if (tags_ == null) {
      return com.google.protobuf.MapField.emptyMapField(
          TagsDefaultEntryHolder.defaultEntry);
    }
    return tags_;
  }

  public int getTagsCount() {
    return internalGetTags().getMap().size();
  }
  /**
   * <code>map&lt;string, string&gt; tags = 2;</code>
   */

  @Override
  public boolean containsTags(
      String key) {
    if (key == null) { throw new NullPointerException(); }
    return internalGetTags().getMap().containsKey(key);
  }
  /**
   * Use {@link #getTagsMap()} instead.
   */
  @Override
  @Deprecated
  public java.util.Map<String, String> getTags() {
    return getTagsMap();
  }
  /**
   * <code>map&lt;string, string&gt; tags = 2;</code>
   */
  @Override

  public java.util.Map<String, String> getTagsMap() {
    return internalGetTags().getMap();
  }
  /**
   * <code>map&lt;string, string&gt; tags = 2;</code>
   */
  @Override

  public String getTagsOrDefault(
      String key,
      String defaultValue) {
    if (key == null) { throw new NullPointerException(); }
    java.util.Map<String, String> map =
        internalGetTags().getMap();
    return map.containsKey(key) ? map.get(key) : defaultValue;
  }
  /**
   * <code>map&lt;string, string&gt; tags = 2;</code>
   */
  @Override

  public String getTagsOrThrow(
      String key) {
    if (key == null) { throw new NullPointerException(); }
    java.util.Map<String, String> map =
        internalGetTags().getMap();
    if (!map.containsKey(key)) {
      throw new IllegalArgumentException();
    }
    return map.get(key);
  }

  public static final int EVENTTIME_FIELD_NUMBER = 3;
  private long eventTime_;
  /**
   * <code>int64 eventTime = 3;</code>
   * @return The eventTime.
   */
  @Override
  public long getEventTime() {
    return eventTime_;
  }

  public static final int VALUE_FIELD_NUMBER = 4;
  private double value_;
  /**
   * <code>double value = 4;</code>
   * @return The value.
   */
  @Override
  public double getValue() {
    return value_;
  }

  public static final int TTL_FIELD_NUMBER = 5;
  private long ttl_;
  /**
   * <code>int64 ttl = 5;</code>
   * @return The ttl.
   */
  @Override
  public long getTtl() {
    return ttl_;
  }

  private byte memoizedIsInitialized = -1;
  @Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getMetricBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, metric_);
    }
    com.google.protobuf.GeneratedMessageV3
      .serializeStringMapTo(
        output,
        internalGetTags(),
        TagsDefaultEntryHolder.defaultEntry,
        2);
    if (eventTime_ != 0L) {
      output.writeInt64(3, eventTime_);
    }
    if (value_ != 0D) {
      output.writeDouble(4, value_);
    }
    if (ttl_ != 0L) {
      output.writeInt64(5, ttl_);
    }
    unknownFields.writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getMetricBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, metric_);
    }
    for (java.util.Map.Entry<String, String> entry
         : internalGetTags().getMap().entrySet()) {
      com.google.protobuf.MapEntry<String, String>
      tags__ = TagsDefaultEntryHolder.defaultEntry.newBuilderForType()
          .setKey(entry.getKey())
          .setValue(entry.getValue())
          .build();
      size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(2, tags__);
    }
    if (eventTime_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(3, eventTime_);
    }
    if (value_ != 0D) {
      size += com.google.protobuf.CodedOutputStream
        .computeDoubleSize(4, value_);
    }
    if (ttl_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(5, ttl_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof MetricValueProto)) {
      return super.equals(obj);
    }
    MetricValueProto other = (MetricValueProto) obj;

    if (!getMetric()
        .equals(other.getMetric())) return false;
    if (!internalGetTags().equals(
        other.internalGetTags())) return false;
    if (getEventTime()
        != other.getEventTime()) return false;
    if (Double.doubleToLongBits(getValue())
        != Double.doubleToLongBits(
            other.getValue())) return false;
    if (getTtl()
        != other.getTtl()) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + METRIC_FIELD_NUMBER;
    hash = (53 * hash) + getMetric().hashCode();
    if (!internalGetTags().getMap().isEmpty()) {
      hash = (37 * hash) + TAGS_FIELD_NUMBER;
      hash = (53 * hash) + internalGetTags().hashCode();
    }
    hash = (37 * hash) + EVENTTIME_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getEventTime());
    hash = (37 * hash) + VALUE_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        Double.doubleToLongBits(getValue()));
    hash = (37 * hash) + TTL_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getTtl());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static MetricValueProto parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static MetricValueProto parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static MetricValueProto parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static MetricValueProto parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static MetricValueProto parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static MetricValueProto parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static MetricValueProto parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static MetricValueProto parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static MetricValueProto parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static MetricValueProto parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static MetricValueProto parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static MetricValueProto parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(MetricValueProto prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @Override
  protected Builder newBuilderForType(
      BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code MetricValueProto}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:MetricValueProto)
      MetricValueProtoOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return MetricProto.internal_static_MetricValueProto_descriptor;
    }

    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapField internalGetMapField(
        int number) {
      switch (number) {
        case 2:
          return internalGetTags();
        default:
          throw new RuntimeException(
              "Invalid map field number: " + number);
      }
    }
    @SuppressWarnings({"rawtypes"})
    protected com.google.protobuf.MapField internalGetMutableMapField(
        int number) {
      switch (number) {
        case 2:
          return internalGetMutableTags();
        default:
          throw new RuntimeException(
              "Invalid map field number: " + number);
      }
    }
    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return MetricProto.internal_static_MetricValueProto_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              MetricValueProto.class, Builder.class);
    }

    // Construct using data.platform.common.protobuf.MetricValueProto.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @Override
    public Builder clear() {
      super.clear();
      metric_ = "";

      internalGetMutableTags().clear();
      eventTime_ = 0L;

      value_ = 0D;

      ttl_ = 0L;

      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return MetricProto.internal_static_MetricValueProto_descriptor;
    }

    @Override
    public MetricValueProto getDefaultInstanceForType() {
      return MetricValueProto.getDefaultInstance();
    }

    @Override
    public MetricValueProto build() {
      MetricValueProto result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public MetricValueProto buildPartial() {
      MetricValueProto result = new MetricValueProto(this);
      int from_bitField0_ = bitField0_;
      result.metric_ = metric_;
      result.tags_ = internalGetTags();
      result.tags_.makeImmutable();
      result.eventTime_ = eventTime_;
      result.value_ = value_;
      result.ttl_ = ttl_;
      onBuilt();
      return result;
    }

    @Override
    public Builder clone() {
      return super.clone();
    }
    @Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return super.setField(field, value);
    }
    @Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return super.addRepeatedField(field, value);
    }
    @Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof MetricValueProto) {
        return mergeFrom((MetricValueProto)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(MetricValueProto other) {
      if (other == MetricValueProto.getDefaultInstance()) return this;
      if (!other.getMetric().isEmpty()) {
        metric_ = other.metric_;
        onChanged();
      }
      internalGetMutableTags().mergeFrom(
          other.internalGetTags());
      if (other.getEventTime() != 0L) {
        setEventTime(other.getEventTime());
      }
      if (other.getValue() != 0D) {
        setValue(other.getValue());
      }
      if (other.getTtl() != 0L) {
        setTtl(other.getTtl());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @Override
    public final boolean isInitialized() {
      return true;
    }

    @Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      MetricValueProto parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (MetricValueProto) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private Object metric_ = "";
    /**
     * <code>string metric = 1;</code>
     * @return The metric.
     */
    public String getMetric() {
      Object ref = metric_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        metric_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <code>string metric = 1;</code>
     * @return The bytes for metric.
     */
    public com.google.protobuf.ByteString
        getMetricBytes() {
      Object ref = metric_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        metric_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string metric = 1;</code>
     * @param value The metric to set.
     * @return This builder for chaining.
     */
    public Builder setMetric(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      metric_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string metric = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearMetric() {
      
      metric_ = getDefaultInstance().getMetric();
      onChanged();
      return this;
    }
    /**
     * <code>string metric = 1;</code>
     * @param value The bytes for metric to set.
     * @return This builder for chaining.
     */
    public Builder setMetricBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      metric_ = value;
      onChanged();
      return this;
    }

    private com.google.protobuf.MapField<
        String, String> tags_;
    private com.google.protobuf.MapField<String, String>
    internalGetTags() {
      if (tags_ == null) {
        return com.google.protobuf.MapField.emptyMapField(
            TagsDefaultEntryHolder.defaultEntry);
      }
      return tags_;
    }
    private com.google.protobuf.MapField<String, String>
    internalGetMutableTags() {
      onChanged();;
      if (tags_ == null) {
        tags_ = com.google.protobuf.MapField.newMapField(
            TagsDefaultEntryHolder.defaultEntry);
      }
      if (!tags_.isMutable()) {
        tags_ = tags_.copy();
      }
      return tags_;
    }

    public int getTagsCount() {
      return internalGetTags().getMap().size();
    }
    /**
     * <code>map&lt;string, string&gt; tags = 2;</code>
     */

    @Override
    public boolean containsTags(
        String key) {
      if (key == null) { throw new NullPointerException(); }
      return internalGetTags().getMap().containsKey(key);
    }
    /**
     * Use {@link #getTagsMap()} instead.
     */
    @Override
    @Deprecated
    public java.util.Map<String, String> getTags() {
      return getTagsMap();
    }
    /**
     * <code>map&lt;string, string&gt; tags = 2;</code>
     */
    @Override

    public java.util.Map<String, String> getTagsMap() {
      return internalGetTags().getMap();
    }
    /**
     * <code>map&lt;string, string&gt; tags = 2;</code>
     */
    @Override

    public String getTagsOrDefault(
        String key,
        String defaultValue) {
      if (key == null) { throw new NullPointerException(); }
      java.util.Map<String, String> map =
          internalGetTags().getMap();
      return map.containsKey(key) ? map.get(key) : defaultValue;
    }
    /**
     * <code>map&lt;string, string&gt; tags = 2;</code>
     */
    @Override

    public String getTagsOrThrow(
        String key) {
      if (key == null) { throw new NullPointerException(); }
      java.util.Map<String, String> map =
          internalGetTags().getMap();
      if (!map.containsKey(key)) {
        throw new IllegalArgumentException();
      }
      return map.get(key);
    }

    public Builder clearTags() {
      internalGetMutableTags().getMutableMap()
          .clear();
      return this;
    }
    /**
     * <code>map&lt;string, string&gt; tags = 2;</code>
     */

    public Builder removeTags(
        String key) {
      if (key == null) { throw new NullPointerException(); }
      internalGetMutableTags().getMutableMap()
          .remove(key);
      return this;
    }
    /**
     * Use alternate mutation accessors instead.
     */
    @Deprecated
    public java.util.Map<String, String>
    getMutableTags() {
      return internalGetMutableTags().getMutableMap();
    }
    /**
     * <code>map&lt;string, string&gt; tags = 2;</code>
     */
    public Builder putTags(
        String key,
        String value) {
      if (key == null) { throw new NullPointerException(); }
      if (value == null) { throw new NullPointerException(); }
      internalGetMutableTags().getMutableMap()
          .put(key, value);
      return this;
    }
    /**
     * <code>map&lt;string, string&gt; tags = 2;</code>
     */

    public Builder putAllTags(
        java.util.Map<String, String> values) {
      internalGetMutableTags().getMutableMap()
          .putAll(values);
      return this;
    }

    private long eventTime_ ;
    /**
     * <code>int64 eventTime = 3;</code>
     * @return The eventTime.
     */
    @Override
    public long getEventTime() {
      return eventTime_;
    }
    /**
     * <code>int64 eventTime = 3;</code>
     * @param value The eventTime to set.
     * @return This builder for chaining.
     */
    public Builder setEventTime(long value) {
      
      eventTime_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 eventTime = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearEventTime() {
      
      eventTime_ = 0L;
      onChanged();
      return this;
    }

    private double value_ ;
    /**
     * <code>double value = 4;</code>
     * @return The value.
     */
    @Override
    public double getValue() {
      return value_;
    }
    /**
     * <code>double value = 4;</code>
     * @param value The value to set.
     * @return This builder for chaining.
     */
    public Builder setValue(double value) {
      
      value_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>double value = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearValue() {
      
      value_ = 0D;
      onChanged();
      return this;
    }

    private long ttl_ ;
    /**
     * <code>int64 ttl = 5;</code>
     * @return The ttl.
     */
    @Override
    public long getTtl() {
      return ttl_;
    }
    /**
     * <code>int64 ttl = 5;</code>
     * @param value The ttl to set.
     * @return This builder for chaining.
     */
    public Builder setTtl(long value) {
      
      ttl_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 ttl = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearTtl() {
      
      ttl_ = 0L;
      onChanged();
      return this;
    }
    @Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:MetricValueProto)
  }

  // @@protoc_insertion_point(class_scope:MetricValueProto)
  private static final MetricValueProto DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new MetricValueProto();
  }

  public static MetricValueProto getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<MetricValueProto>
      PARSER = new com.google.protobuf.AbstractParser<MetricValueProto>() {
    @Override
    public MetricValueProto parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new MetricValueProto(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<MetricValueProto> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<MetricValueProto> getParserForType() {
    return PARSER;
  }

  @Override
  public MetricValueProto getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

