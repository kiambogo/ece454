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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-5-26")
public class ServerUpBroadcast implements org.apache.thrift.TBase<ServerUpBroadcast, ServerUpBroadcast._Fields>, java.io.Serializable, Cloneable, Comparable<ServerUpBroadcast> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ServerUpBroadcast");

  private static final org.apache.thrift.protocol.TField PORT_NUMBER_FIELD_DESC = new org.apache.thrift.protocol.TField("portNumber", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField NUMBER_OF_CORES_FIELD_DESC = new org.apache.thrift.protocol.TField("numberOfCores", org.apache.thrift.protocol.TType.I32, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ServerUpBroadcastStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ServerUpBroadcastTupleSchemeFactory());
  }

  public int portNumber; // required
  public int numberOfCores; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PORT_NUMBER((short)1, "portNumber"),
    NUMBER_OF_CORES((short)2, "numberOfCores");

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
        case 1: // PORT_NUMBER
          return PORT_NUMBER;
        case 2: // NUMBER_OF_CORES
          return NUMBER_OF_CORES;
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
  private static final int __PORTNUMBER_ISSET_ID = 0;
  private static final int __NUMBEROFCORES_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PORT_NUMBER, new org.apache.thrift.meta_data.FieldMetaData("portNumber", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.NUMBER_OF_CORES, new org.apache.thrift.meta_data.FieldMetaData("numberOfCores", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ServerUpBroadcast.class, metaDataMap);
  }

  public ServerUpBroadcast() {
  }

  public ServerUpBroadcast(
    int portNumber,
    int numberOfCores)
  {
    this();
    this.portNumber = portNumber;
    setPortNumberIsSet(true);
    this.numberOfCores = numberOfCores;
    setNumberOfCoresIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ServerUpBroadcast(ServerUpBroadcast other) {
    __isset_bitfield = other.__isset_bitfield;
    this.portNumber = other.portNumber;
    this.numberOfCores = other.numberOfCores;
  }

  public ServerUpBroadcast deepCopy() {
    return new ServerUpBroadcast(this);
  }

  @Override
  public void clear() {
    setPortNumberIsSet(false);
    this.portNumber = 0;
    setNumberOfCoresIsSet(false);
    this.numberOfCores = 0;
  }

  public int getPortNumber() {
    return this.portNumber;
  }

  public ServerUpBroadcast setPortNumber(int portNumber) {
    this.portNumber = portNumber;
    setPortNumberIsSet(true);
    return this;
  }

  public void unsetPortNumber() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PORTNUMBER_ISSET_ID);
  }

  /** Returns true if field portNumber is set (has been assigned a value) and false otherwise */
  public boolean isSetPortNumber() {
    return EncodingUtils.testBit(__isset_bitfield, __PORTNUMBER_ISSET_ID);
  }

  public void setPortNumberIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PORTNUMBER_ISSET_ID, value);
  }

  public int getNumberOfCores() {
    return this.numberOfCores;
  }

  public ServerUpBroadcast setNumberOfCores(int numberOfCores) {
    this.numberOfCores = numberOfCores;
    setNumberOfCoresIsSet(true);
    return this;
  }

  public void unsetNumberOfCores() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __NUMBEROFCORES_ISSET_ID);
  }

  /** Returns true if field numberOfCores is set (has been assigned a value) and false otherwise */
  public boolean isSetNumberOfCores() {
    return EncodingUtils.testBit(__isset_bitfield, __NUMBEROFCORES_ISSET_ID);
  }

  public void setNumberOfCoresIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __NUMBEROFCORES_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PORT_NUMBER:
      if (value == null) {
        unsetPortNumber();
      } else {
        setPortNumber((Integer)value);
      }
      break;

    case NUMBER_OF_CORES:
      if (value == null) {
        unsetNumberOfCores();
      } else {
        setNumberOfCores((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PORT_NUMBER:
      return Integer.valueOf(getPortNumber());

    case NUMBER_OF_CORES:
      return Integer.valueOf(getNumberOfCores());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PORT_NUMBER:
      return isSetPortNumber();
    case NUMBER_OF_CORES:
      return isSetNumberOfCores();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ServerUpBroadcast)
      return this.equals((ServerUpBroadcast)that);
    return false;
  }

  public boolean equals(ServerUpBroadcast that) {
    if (that == null)
      return false;

    boolean this_present_portNumber = true;
    boolean that_present_portNumber = true;
    if (this_present_portNumber || that_present_portNumber) {
      if (!(this_present_portNumber && that_present_portNumber))
        return false;
      if (this.portNumber != that.portNumber)
        return false;
    }

    boolean this_present_numberOfCores = true;
    boolean that_present_numberOfCores = true;
    if (this_present_numberOfCores || that_present_numberOfCores) {
      if (!(this_present_numberOfCores && that_present_numberOfCores))
        return false;
      if (this.numberOfCores != that.numberOfCores)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_portNumber = true;
    list.add(present_portNumber);
    if (present_portNumber)
      list.add(portNumber);

    boolean present_numberOfCores = true;
    list.add(present_numberOfCores);
    if (present_numberOfCores)
      list.add(numberOfCores);

    return list.hashCode();
  }

  @Override
  public int compareTo(ServerUpBroadcast other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetPortNumber()).compareTo(other.isSetPortNumber());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPortNumber()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.portNumber, other.portNumber);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetNumberOfCores()).compareTo(other.isSetNumberOfCores());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNumberOfCores()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.numberOfCores, other.numberOfCores);
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
    StringBuilder sb = new StringBuilder("ServerUpBroadcast(");
    boolean first = true;

    sb.append("portNumber:");
    sb.append(this.portNumber);
    first = false;
    if (!first) sb.append(", ");
    sb.append("numberOfCores:");
    sb.append(this.numberOfCores);
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ServerUpBroadcastStandardSchemeFactory implements SchemeFactory {
    public ServerUpBroadcastStandardScheme getScheme() {
      return new ServerUpBroadcastStandardScheme();
    }
  }

  private static class ServerUpBroadcastStandardScheme extends StandardScheme<ServerUpBroadcast> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ServerUpBroadcast struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PORT_NUMBER
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.portNumber = iprot.readI32();
              struct.setPortNumberIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // NUMBER_OF_CORES
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.numberOfCores = iprot.readI32();
              struct.setNumberOfCoresIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ServerUpBroadcast struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(PORT_NUMBER_FIELD_DESC);
      oprot.writeI32(struct.portNumber);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(NUMBER_OF_CORES_FIELD_DESC);
      oprot.writeI32(struct.numberOfCores);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ServerUpBroadcastTupleSchemeFactory implements SchemeFactory {
    public ServerUpBroadcastTupleScheme getScheme() {
      return new ServerUpBroadcastTupleScheme();
    }
  }

  private static class ServerUpBroadcastTupleScheme extends TupleScheme<ServerUpBroadcast> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ServerUpBroadcast struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPortNumber()) {
        optionals.set(0);
      }
      if (struct.isSetNumberOfCores()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPortNumber()) {
        oprot.writeI32(struct.portNumber);
      }
      if (struct.isSetNumberOfCores()) {
        oprot.writeI32(struct.numberOfCores);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ServerUpBroadcast struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.portNumber = iprot.readI32();
        struct.setPortNumberIsSet(true);
      }
      if (incoming.get(1)) {
        struct.numberOfCores = iprot.readI32();
        struct.setNumberOfCoresIsSet(true);
      }
    }
  }

}
