// generated from rosidl_generator_c/resource/idl__struct.h.em
// with input from interfaces:msg/Arm2Heartbeat.idl
// generated code does not contain a copyright notice

#ifndef INTERFACES__MSG__DETAIL__ARM2_HEARTBEAT__STRUCT_H_
#define INTERFACES__MSG__DETAIL__ARM2_HEARTBEAT__STRUCT_H_

#ifdef __cplusplus
extern "C"
{
#endif

#include <stdbool.h>
#include <stddef.h>
#include <stdint.h>


// Constants defined in the message

/// Struct defined in msg/Arm2Heartbeat in the package interfaces.
typedef struct interfaces__msg__Arm2Heartbeat
{
  int32_t number;
} interfaces__msg__Arm2Heartbeat;

// Struct for a sequence of interfaces__msg__Arm2Heartbeat.
typedef struct interfaces__msg__Arm2Heartbeat__Sequence
{
  interfaces__msg__Arm2Heartbeat * data;
  /// The number of valid items in data
  size_t size;
  /// The number of allocated items in data
  size_t capacity;
} interfaces__msg__Arm2Heartbeat__Sequence;

#ifdef __cplusplus
}
#endif

#endif  // INTERFACES__MSG__DETAIL__ARM2_HEARTBEAT__STRUCT_H_
