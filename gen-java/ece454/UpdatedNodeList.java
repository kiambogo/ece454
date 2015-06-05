/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package ece454;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-6-5")
public class UpdatedNodeList implements org.apache.thrift.TBase<UpdatedNodeList, UpdatedNodeList._Fields>, java.io.Serializable, Cloneable, Comparable<UpdatedNodeList> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("UpdatedNodeList");

  private static final org.apache.thrift.protocol.TField TIMESTAMP_FIELD_DESC = new org.apache.thrift.protocol.TField("timestamp", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField BE_NODES_FIELD_DESC = new org.apache.thrift.protocol.TField("beNodes", org.apache.thrift.protocol.TType.LIST, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new UpdatedNodeListStandardSchemeFactory());
    schemes.put(TupleScheme.class, new UpdatedNodeListTupleSchemeFactory());
  }

  public String timestamp; // required
  public List<TimedHeartbeat> beNodes; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TIMESTAMP((short)1, "timestamp"),
    BE_NODES((short)3, "beNodes");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // TIMESTAMP
          return TIMESTAMP;
        case 3: // BE_NODES
          return BE_NODES;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TIMESTAMP, new org.apache.thrift.meta_data.FieldMetaData("timestamp", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.BE_NODES, new org.apache.thrift.meta_data.FieldMetaData("beNodes", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TimedHeartbeat.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(UpdatedNodeList.class, metaDataMap);
  }

  public UpdatedNodeList() {
  }

  public UpdatedNodeList(
    String timestamp,
    List<TimedHeartbeat> beNodes)
  {
    this();
    this.timestamp = timestamp;
    this.beNodes = beNodes;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public UpdatedNodeList(UpdatedNodeList other) {
    if (other.isSetTimestamp()) {
      this.timestamp = other.timestamp;
    }
    if (other.isSetBeNodes()) {
      List<TimedHeartbeat> __this__beNodes = new ArrayList<TimedHeartbeat>(other.beNodes.size());
      for (TimedHeartbeat other_element : other.beNodes) {
        __this__beNodes.add(new TimedHeartbeat(other_element));
      }
      this.beNodes = __this__beNodes;
    }
  }

  public UpdatedNodeList deepCopy() {
    return new UpdatedNodeList(this);
  }

  @Override
  public void clear() {
    this.timestamp = null;
    this.beNodes = null;
  }

  public String getTimestamp() {
    return this.timestamp;
  }

  public UpdatedNodeList setTimestamp(String timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  public void unsetTimestamp() {
    this.timestamp = null;
  }

  /** Returns true if field timestamp is set (has been assigned a value) and false otherwise */
  public boolean isSetTimestamp() {
    return this.timestamp != null;
  }

  public void setTimestampIsSet(boolean value) {
    if (!value) {
      this.timestamp = null;
    }
  }

  public int getBeNodesSize() {
    return (this.beNodes == null) ? 0 : this.beNodes.size();
  }

  public java.util.Iterator<TimedHeartbeat> getBeNodesIterator() {
    return (this.beNodes == null) ? null : this.beNodes.iterator();
  }

  public void addToBeNodes(TimedHeartbeat elem) {
    if (this.beNodes == null) {
      this.beNodes = new ArrayList<TimedHeartbeat>();
    }
    this.beNodes.add(elem);
  }

  public List<TimedHeartbeat> getBeNodes() {
    return this.beNodes;
  }

  public UpdatedNodeList setBeNodes(List<TimedHeartbeat> beNodes) {
    this.beNodes = beNodes;
    return this;
  }

  public void unsetBeNodes() {
    this.beNodes = null;
  }

  /** Returns true if field beNodes is set (has been assigned a value) and false otherwise */
  public boolean isSetBeNodes() {
    return this.beNodes != null;
  }

  public void setBeNodesIsSet(boolean value) {
    if (!value) {
      this.beNodes = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TIMESTAMP:
      if (value == null) {
        unsetTimestamp();
      } else {
        setTimestamp((String)value);
      }
      break;

    case BE_NODES:
      if (value == null) {
        unsetBeNodes();
      } else {
        setBeNodes((List<TimedHeartbeat>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TIMESTAMP:
      return getTimestamp();

    case BE_NODES:
      return getBeNodes();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TIMESTAMP:
      return isSetTimestamp();
    case BE_NODES:
      return isSetBeNodes();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof UpdatedNodeList)
      return this.equals((UpdatedNodeList)that);
    return false;
  }

  public boolean equals(UpdatedNodeList that) {
    if (that == null)
      return false;

    boolean this_present_timestamp = true && this.isSetTimestamp();
    boolean that_present_timestamp = true && that.isSetTimestamp();
    if (this_present_timestamp || that_present_timestamp) {
      if (!(this_present_timestamp && that_present_timestamp))
        return false;
      if (!this.timestamp.equals(that.timestamp))
        return false;
    }

    boolean this_present_beNodes = true && this.isSetBeNodes();
    boolean that_present_beNodes = true && that.isSetBeNodes();
    if (this_present_beNodes || that_present_beNodes) {
      if (!(this_present_beNodes && that_present_beNodes))
        return false;
      if (!this.beNodes.equals(that.beNodes))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_timestamp = true && (isSetTimestamp());
    list.add(present_timestamp);
    if (present_timestamp)
      list.add(timestamp);

    boolean present_beNodes = true && (isSetBeNodes());
    list.add(present_beNodes);
    if (present_beNodes)
      list.add(beNodes);

    return list.hashCode();
  }

  @Override
  public int compareTo(UpdatedNodeList other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTimestamp()).compareTo(other.isSetTimestamp());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTimestamp()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.timestamp, other.timestamp);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBeNodes()).compareTo(other.isSetBeNodes());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBeNodes()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.beNodes, other.beNodes);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("UpdatedNodeList(");
    boolean first = true;

    sb.append("timestamp:");
    if (this.timestamp == null) {
      sb.append("null");
    } else {
      sb.append(this.timestamp);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("beNodes:");
    if (this.beNodes == null) {
      sb.append("null");
    } else {
      sb.append(this.beNodes);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class UpdatedNodeListStandardSchemeFactory implements SchemeFactory {
    public UpdatedNodeListStandardScheme getScheme() {
      return new UpdatedNodeListStandardScheme();
    }
  }

  private static class UpdatedNodeListStandardScheme extends StandardScheme<UpdatedNodeList> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, UpdatedNodeList struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TIMESTAMP
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.timestamp = iprot.readString();
              struct.setTimestampIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // BE_NODES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.beNodes = new ArrayList<TimedHeartbeat>(_list0.size);
                TimedHeartbeat _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = new TimedHeartbeat();
                  _elem1.read(iprot);
                  struct.beNodes.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setBeNodesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, UpdatedNodeList struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.timestamp != null) {
        oprot.writeFieldBegin(TIMESTAMP_FIELD_DESC);
        oprot.writeString(struct.timestamp);
        oprot.writeFieldEnd();
      }
      if (struct.beNodes != null) {
        oprot.writeFieldBegin(BE_NODES_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.beNodes.size()));
          for (TimedHeartbeat _iter3 : struct.beNodes)
          {
            _iter3.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class UpdatedNodeListTupleSchemeFactory implements SchemeFactory {
    public UpdatedNodeListTupleScheme getScheme() {
      return new UpdatedNodeListTupleScheme();
    }
  }

  private static class UpdatedNodeListTupleScheme extends TupleScheme<UpdatedNodeList> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, UpdatedNodeList struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTimestamp()) {
        optionals.set(0);
      }
      if (struct.isSetBeNodes()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetTimestamp()) {
        oprot.writeString(struct.timestamp);
      }
      if (struct.isSetBeNodes()) {
        {
          oprot.writeI32(struct.beNodes.size());
          for (TimedHeartbeat _iter4 : struct.beNodes)
          {
            _iter4.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, UpdatedNodeList struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.timestamp = iprot.readString();
        struct.setTimestampIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.beNodes = new ArrayList<TimedHeartbeat>(_list5.size);
          TimedHeartbeat _elem6;
          for (int _i7 = 0; _i7 < _list5.size; ++_i7)
          {
            _elem6 = new TimedHeartbeat();
            _elem6.read(iprot);
            struct.beNodes.add(_elem6);
          }
        }
        struct.setBeNodesIsSet(true);
      }
    }
  }

}
