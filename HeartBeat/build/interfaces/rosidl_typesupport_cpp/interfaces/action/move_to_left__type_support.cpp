// generated from rosidl_typesupport_cpp/resource/idl__type_support.cpp.em
// with input from interfaces:action/MoveToLeft.idl
// generated code does not contain a copyright notice

#include "cstddef"
#include "rosidl_runtime_c/message_type_support_struct.h"
#include "interfaces/action/detail/move_to_left__struct.hpp"
#include "rosidl_typesupport_cpp/identifier.hpp"
#include "rosidl_typesupport_cpp/message_type_support.hpp"
#include "rosidl_typesupport_c/type_support_map.h"
#include "rosidl_typesupport_cpp/message_type_support_dispatch.hpp"
#include "rosidl_typesupport_cpp/visibility_control.h"
#include "rosidl_typesupport_interface/macros.h"

namespace interfaces
{

namespace action
{

namespace rosidl_typesupport_cpp
{

typedef struct _MoveToLeft_Goal_type_support_ids_t
{
  const char * typesupport_identifier[2];
} _MoveToLeft_Goal_type_support_ids_t;

static const _MoveToLeft_Goal_type_support_ids_t _MoveToLeft_Goal_message_typesupport_ids = {
  {
    "rosidl_typesupport_fastrtps_cpp",  // ::rosidl_typesupport_fastrtps_cpp::typesupport_identifier,
    "rosidl_typesupport_introspection_cpp",  // ::rosidl_typesupport_introspection_cpp::typesupport_identifier,
  }
};

typedef struct _MoveToLeft_Goal_type_support_symbol_names_t
{
  const char * symbol_name[2];
} _MoveToLeft_Goal_type_support_symbol_names_t;

#define STRINGIFY_(s) #s
#define STRINGIFY(s) STRINGIFY_(s)

static const _MoveToLeft_Goal_type_support_symbol_names_t _MoveToLeft_Goal_message_typesupport_symbol_names = {
  {
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_fastrtps_cpp, interfaces, action, MoveToLeft_Goal)),
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_cpp, interfaces, action, MoveToLeft_Goal)),
  }
};

typedef struct _MoveToLeft_Goal_type_support_data_t
{
  void * data[2];
} _MoveToLeft_Goal_type_support_data_t;

static _MoveToLeft_Goal_type_support_data_t _MoveToLeft_Goal_message_typesupport_data = {
  {
    0,  // will store the shared library later
    0,  // will store the shared library later
  }
};

static const type_support_map_t _MoveToLeft_Goal_message_typesupport_map = {
  2,
  "interfaces",
  &_MoveToLeft_Goal_message_typesupport_ids.typesupport_identifier[0],
  &_MoveToLeft_Goal_message_typesupport_symbol_names.symbol_name[0],
  &_MoveToLeft_Goal_message_typesupport_data.data[0],
};

static const rosidl_message_type_support_t MoveToLeft_Goal_message_type_support_handle = {
  ::rosidl_typesupport_cpp::typesupport_identifier,
  reinterpret_cast<const type_support_map_t *>(&_MoveToLeft_Goal_message_typesupport_map),
  ::rosidl_typesupport_cpp::get_message_typesupport_handle_function,
};

}  // namespace rosidl_typesupport_cpp

}  // namespace action

}  // namespace interfaces

namespace rosidl_typesupport_cpp
{

template<>
ROSIDL_TYPESUPPORT_CPP_PUBLIC
const rosidl_message_type_support_t *
get_message_type_support_handle<interfaces::action::MoveToLeft_Goal>()
{
  return &::interfaces::action::rosidl_typesupport_cpp::MoveToLeft_Goal_message_type_support_handle;
}

#ifdef __cplusplus
extern "C"
{
#endif

ROSIDL_TYPESUPPORT_CPP_PUBLIC
const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_cpp, interfaces, action, MoveToLeft_Goal)() {
  return get_message_type_support_handle<interfaces::action::MoveToLeft_Goal>();
}

#ifdef __cplusplus
}
#endif
}  // namespace rosidl_typesupport_cpp

// already included above
// #include "cstddef"
// already included above
// #include "rosidl_runtime_c/message_type_support_struct.h"
// already included above
// #include "interfaces/action/detail/move_to_left__struct.hpp"
// already included above
// #include "rosidl_typesupport_cpp/identifier.hpp"
// already included above
// #include "rosidl_typesupport_cpp/message_type_support.hpp"
// already included above
// #include "rosidl_typesupport_c/type_support_map.h"
// already included above
// #include "rosidl_typesupport_cpp/message_type_support_dispatch.hpp"
// already included above
// #include "rosidl_typesupport_cpp/visibility_control.h"
// already included above
// #include "rosidl_typesupport_interface/macros.h"

namespace interfaces
{

namespace action
{

namespace rosidl_typesupport_cpp
{

typedef struct _MoveToLeft_Result_type_support_ids_t
{
  const char * typesupport_identifier[2];
} _MoveToLeft_Result_type_support_ids_t;

static const _MoveToLeft_Result_type_support_ids_t _MoveToLeft_Result_message_typesupport_ids = {
  {
    "rosidl_typesupport_fastrtps_cpp",  // ::rosidl_typesupport_fastrtps_cpp::typesupport_identifier,
    "rosidl_typesupport_introspection_cpp",  // ::rosidl_typesupport_introspection_cpp::typesupport_identifier,
  }
};

typedef struct _MoveToLeft_Result_type_support_symbol_names_t
{
  const char * symbol_name[2];
} _MoveToLeft_Result_type_support_symbol_names_t;

#define STRINGIFY_(s) #s
#define STRINGIFY(s) STRINGIFY_(s)

static const _MoveToLeft_Result_type_support_symbol_names_t _MoveToLeft_Result_message_typesupport_symbol_names = {
  {
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_fastrtps_cpp, interfaces, action, MoveToLeft_Result)),
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_cpp, interfaces, action, MoveToLeft_Result)),
  }
};

typedef struct _MoveToLeft_Result_type_support_data_t
{
  void * data[2];
} _MoveToLeft_Result_type_support_data_t;

static _MoveToLeft_Result_type_support_data_t _MoveToLeft_Result_message_typesupport_data = {
  {
    0,  // will store the shared library later
    0,  // will store the shared library later
  }
};

static const type_support_map_t _MoveToLeft_Result_message_typesupport_map = {
  2,
  "interfaces",
  &_MoveToLeft_Result_message_typesupport_ids.typesupport_identifier[0],
  &_MoveToLeft_Result_message_typesupport_symbol_names.symbol_name[0],
  &_MoveToLeft_Result_message_typesupport_data.data[0],
};

static const rosidl_message_type_support_t MoveToLeft_Result_message_type_support_handle = {
  ::rosidl_typesupport_cpp::typesupport_identifier,
  reinterpret_cast<const type_support_map_t *>(&_MoveToLeft_Result_message_typesupport_map),
  ::rosidl_typesupport_cpp::get_message_typesupport_handle_function,
};

}  // namespace rosidl_typesupport_cpp

}  // namespace action

}  // namespace interfaces

namespace rosidl_typesupport_cpp
{

template<>
ROSIDL_TYPESUPPORT_CPP_PUBLIC
const rosidl_message_type_support_t *
get_message_type_support_handle<interfaces::action::MoveToLeft_Result>()
{
  return &::interfaces::action::rosidl_typesupport_cpp::MoveToLeft_Result_message_type_support_handle;
}

#ifdef __cplusplus
extern "C"
{
#endif

ROSIDL_TYPESUPPORT_CPP_PUBLIC
const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_cpp, interfaces, action, MoveToLeft_Result)() {
  return get_message_type_support_handle<interfaces::action::MoveToLeft_Result>();
}

#ifdef __cplusplus
}
#endif
}  // namespace rosidl_typesupport_cpp

// already included above
// #include "cstddef"
// already included above
// #include "rosidl_runtime_c/message_type_support_struct.h"
// already included above
// #include "interfaces/action/detail/move_to_left__struct.hpp"
// already included above
// #include "rosidl_typesupport_cpp/identifier.hpp"
// already included above
// #include "rosidl_typesupport_cpp/message_type_support.hpp"
// already included above
// #include "rosidl_typesupport_c/type_support_map.h"
// already included above
// #include "rosidl_typesupport_cpp/message_type_support_dispatch.hpp"
// already included above
// #include "rosidl_typesupport_cpp/visibility_control.h"
// already included above
// #include "rosidl_typesupport_interface/macros.h"

namespace interfaces
{

namespace action
{

namespace rosidl_typesupport_cpp
{

typedef struct _MoveToLeft_Feedback_type_support_ids_t
{
  const char * typesupport_identifier[2];
} _MoveToLeft_Feedback_type_support_ids_t;

static const _MoveToLeft_Feedback_type_support_ids_t _MoveToLeft_Feedback_message_typesupport_ids = {
  {
    "rosidl_typesupport_fastrtps_cpp",  // ::rosidl_typesupport_fastrtps_cpp::typesupport_identifier,
    "rosidl_typesupport_introspection_cpp",  // ::rosidl_typesupport_introspection_cpp::typesupport_identifier,
  }
};

typedef struct _MoveToLeft_Feedback_type_support_symbol_names_t
{
  const char * symbol_name[2];
} _MoveToLeft_Feedback_type_support_symbol_names_t;

#define STRINGIFY_(s) #s
#define STRINGIFY(s) STRINGIFY_(s)

static const _MoveToLeft_Feedback_type_support_symbol_names_t _MoveToLeft_Feedback_message_typesupport_symbol_names = {
  {
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_fastrtps_cpp, interfaces, action, MoveToLeft_Feedback)),
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_cpp, interfaces, action, MoveToLeft_Feedback)),
  }
};

typedef struct _MoveToLeft_Feedback_type_support_data_t
{
  void * data[2];
} _MoveToLeft_Feedback_type_support_data_t;

static _MoveToLeft_Feedback_type_support_data_t _MoveToLeft_Feedback_message_typesupport_data = {
  {
    0,  // will store the shared library later
    0,  // will store the shared library later
  }
};

static const type_support_map_t _MoveToLeft_Feedback_message_typesupport_map = {
  2,
  "interfaces",
  &_MoveToLeft_Feedback_message_typesupport_ids.typesupport_identifier[0],
  &_MoveToLeft_Feedback_message_typesupport_symbol_names.symbol_name[0],
  &_MoveToLeft_Feedback_message_typesupport_data.data[0],
};

static const rosidl_message_type_support_t MoveToLeft_Feedback_message_type_support_handle = {
  ::rosidl_typesupport_cpp::typesupport_identifier,
  reinterpret_cast<const type_support_map_t *>(&_MoveToLeft_Feedback_message_typesupport_map),
  ::rosidl_typesupport_cpp::get_message_typesupport_handle_function,
};

}  // namespace rosidl_typesupport_cpp

}  // namespace action

}  // namespace interfaces

namespace rosidl_typesupport_cpp
{

template<>
ROSIDL_TYPESUPPORT_CPP_PUBLIC
const rosidl_message_type_support_t *
get_message_type_support_handle<interfaces::action::MoveToLeft_Feedback>()
{
  return &::interfaces::action::rosidl_typesupport_cpp::MoveToLeft_Feedback_message_type_support_handle;
}

#ifdef __cplusplus
extern "C"
{
#endif

ROSIDL_TYPESUPPORT_CPP_PUBLIC
const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_cpp, interfaces, action, MoveToLeft_Feedback)() {
  return get_message_type_support_handle<interfaces::action::MoveToLeft_Feedback>();
}

#ifdef __cplusplus
}
#endif
}  // namespace rosidl_typesupport_cpp

// already included above
// #include "cstddef"
// already included above
// #include "rosidl_runtime_c/message_type_support_struct.h"
// already included above
// #include "interfaces/action/detail/move_to_left__struct.hpp"
// already included above
// #include "rosidl_typesupport_cpp/identifier.hpp"
// already included above
// #include "rosidl_typesupport_cpp/message_type_support.hpp"
// already included above
// #include "rosidl_typesupport_c/type_support_map.h"
// already included above
// #include "rosidl_typesupport_cpp/message_type_support_dispatch.hpp"
// already included above
// #include "rosidl_typesupport_cpp/visibility_control.h"
// already included above
// #include "rosidl_typesupport_interface/macros.h"

namespace interfaces
{

namespace action
{

namespace rosidl_typesupport_cpp
{

typedef struct _MoveToLeft_SendGoal_Request_type_support_ids_t
{
  const char * typesupport_identifier[2];
} _MoveToLeft_SendGoal_Request_type_support_ids_t;

static const _MoveToLeft_SendGoal_Request_type_support_ids_t _MoveToLeft_SendGoal_Request_message_typesupport_ids = {
  {
    "rosidl_typesupport_fastrtps_cpp",  // ::rosidl_typesupport_fastrtps_cpp::typesupport_identifier,
    "rosidl_typesupport_introspection_cpp",  // ::rosidl_typesupport_introspection_cpp::typesupport_identifier,
  }
};

typedef struct _MoveToLeft_SendGoal_Request_type_support_symbol_names_t
{
  const char * symbol_name[2];
} _MoveToLeft_SendGoal_Request_type_support_symbol_names_t;

#define STRINGIFY_(s) #s
#define STRINGIFY(s) STRINGIFY_(s)

static const _MoveToLeft_SendGoal_Request_type_support_symbol_names_t _MoveToLeft_SendGoal_Request_message_typesupport_symbol_names = {
  {
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_fastrtps_cpp, interfaces, action, MoveToLeft_SendGoal_Request)),
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_cpp, interfaces, action, MoveToLeft_SendGoal_Request)),
  }
};

typedef struct _MoveToLeft_SendGoal_Request_type_support_data_t
{
  void * data[2];
} _MoveToLeft_SendGoal_Request_type_support_data_t;

static _MoveToLeft_SendGoal_Request_type_support_data_t _MoveToLeft_SendGoal_Request_message_typesupport_data = {
  {
    0,  // will store the shared library later
    0,  // will store the shared library later
  }
};

static const type_support_map_t _MoveToLeft_SendGoal_Request_message_typesupport_map = {
  2,
  "interfaces",
  &_MoveToLeft_SendGoal_Request_message_typesupport_ids.typesupport_identifier[0],
  &_MoveToLeft_SendGoal_Request_message_typesupport_symbol_names.symbol_name[0],
  &_MoveToLeft_SendGoal_Request_message_typesupport_data.data[0],
};

static const rosidl_message_type_support_t MoveToLeft_SendGoal_Request_message_type_support_handle = {
  ::rosidl_typesupport_cpp::typesupport_identifier,
  reinterpret_cast<const type_support_map_t *>(&_MoveToLeft_SendGoal_Request_message_typesupport_map),
  ::rosidl_typesupport_cpp::get_message_typesupport_handle_function,
};

}  // namespace rosidl_typesupport_cpp

}  // namespace action

}  // namespace interfaces

namespace rosidl_typesupport_cpp
{

template<>
ROSIDL_TYPESUPPORT_CPP_PUBLIC
const rosidl_message_type_support_t *
get_message_type_support_handle<interfaces::action::MoveToLeft_SendGoal_Request>()
{
  return &::interfaces::action::rosidl_typesupport_cpp::MoveToLeft_SendGoal_Request_message_type_support_handle;
}

#ifdef __cplusplus
extern "C"
{
#endif

ROSIDL_TYPESUPPORT_CPP_PUBLIC
const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_cpp, interfaces, action, MoveToLeft_SendGoal_Request)() {
  return get_message_type_support_handle<interfaces::action::MoveToLeft_SendGoal_Request>();
}

#ifdef __cplusplus
}
#endif
}  // namespace rosidl_typesupport_cpp

// already included above
// #include "cstddef"
// already included above
// #include "rosidl_runtime_c/message_type_support_struct.h"
// already included above
// #include "interfaces/action/detail/move_to_left__struct.hpp"
// already included above
// #include "rosidl_typesupport_cpp/identifier.hpp"
// already included above
// #include "rosidl_typesupport_cpp/message_type_support.hpp"
// already included above
// #include "rosidl_typesupport_c/type_support_map.h"
// already included above
// #include "rosidl_typesupport_cpp/message_type_support_dispatch.hpp"
// already included above
// #include "rosidl_typesupport_cpp/visibility_control.h"
// already included above
// #include "rosidl_typesupport_interface/macros.h"

namespace interfaces
{

namespace action
{

namespace rosidl_typesupport_cpp
{

typedef struct _MoveToLeft_SendGoal_Response_type_support_ids_t
{
  const char * typesupport_identifier[2];
} _MoveToLeft_SendGoal_Response_type_support_ids_t;

static const _MoveToLeft_SendGoal_Response_type_support_ids_t _MoveToLeft_SendGoal_Response_message_typesupport_ids = {
  {
    "rosidl_typesupport_fastrtps_cpp",  // ::rosidl_typesupport_fastrtps_cpp::typesupport_identifier,
    "rosidl_typesupport_introspection_cpp",  // ::rosidl_typesupport_introspection_cpp::typesupport_identifier,
  }
};

typedef struct _MoveToLeft_SendGoal_Response_type_support_symbol_names_t
{
  const char * symbol_name[2];
} _MoveToLeft_SendGoal_Response_type_support_symbol_names_t;

#define STRINGIFY_(s) #s
#define STRINGIFY(s) STRINGIFY_(s)

static const _MoveToLeft_SendGoal_Response_type_support_symbol_names_t _MoveToLeft_SendGoal_Response_message_typesupport_symbol_names = {
  {
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_fastrtps_cpp, interfaces, action, MoveToLeft_SendGoal_Response)),
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_cpp, interfaces, action, MoveToLeft_SendGoal_Response)),
  }
};

typedef struct _MoveToLeft_SendGoal_Response_type_support_data_t
{
  void * data[2];
} _MoveToLeft_SendGoal_Response_type_support_data_t;

static _MoveToLeft_SendGoal_Response_type_support_data_t _MoveToLeft_SendGoal_Response_message_typesupport_data = {
  {
    0,  // will store the shared library later
    0,  // will store the shared library later
  }
};

static const type_support_map_t _MoveToLeft_SendGoal_Response_message_typesupport_map = {
  2,
  "interfaces",
  &_MoveToLeft_SendGoal_Response_message_typesupport_ids.typesupport_identifier[0],
  &_MoveToLeft_SendGoal_Response_message_typesupport_symbol_names.symbol_name[0],
  &_MoveToLeft_SendGoal_Response_message_typesupport_data.data[0],
};

static const rosidl_message_type_support_t MoveToLeft_SendGoal_Response_message_type_support_handle = {
  ::rosidl_typesupport_cpp::typesupport_identifier,
  reinterpret_cast<const type_support_map_t *>(&_MoveToLeft_SendGoal_Response_message_typesupport_map),
  ::rosidl_typesupport_cpp::get_message_typesupport_handle_function,
};

}  // namespace rosidl_typesupport_cpp

}  // namespace action

}  // namespace interfaces

namespace rosidl_typesupport_cpp
{

template<>
ROSIDL_TYPESUPPORT_CPP_PUBLIC
const rosidl_message_type_support_t *
get_message_type_support_handle<interfaces::action::MoveToLeft_SendGoal_Response>()
{
  return &::interfaces::action::rosidl_typesupport_cpp::MoveToLeft_SendGoal_Response_message_type_support_handle;
}

#ifdef __cplusplus
extern "C"
{
#endif

ROSIDL_TYPESUPPORT_CPP_PUBLIC
const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_cpp, interfaces, action, MoveToLeft_SendGoal_Response)() {
  return get_message_type_support_handle<interfaces::action::MoveToLeft_SendGoal_Response>();
}

#ifdef __cplusplus
}
#endif
}  // namespace rosidl_typesupport_cpp

// already included above
// #include "cstddef"
#include "rosidl_runtime_c/service_type_support_struct.h"
// already included above
// #include "interfaces/action/detail/move_to_left__struct.hpp"
// already included above
// #include "rosidl_typesupport_cpp/identifier.hpp"
#include "rosidl_typesupport_cpp/service_type_support.hpp"
// already included above
// #include "rosidl_typesupport_c/type_support_map.h"
#include "rosidl_typesupport_cpp/service_type_support_dispatch.hpp"
// already included above
// #include "rosidl_typesupport_cpp/visibility_control.h"
// already included above
// #include "rosidl_typesupport_interface/macros.h"

namespace interfaces
{

namespace action
{

namespace rosidl_typesupport_cpp
{

typedef struct _MoveToLeft_SendGoal_type_support_ids_t
{
  const char * typesupport_identifier[2];
} _MoveToLeft_SendGoal_type_support_ids_t;

static const _MoveToLeft_SendGoal_type_support_ids_t _MoveToLeft_SendGoal_service_typesupport_ids = {
  {
    "rosidl_typesupport_fastrtps_cpp",  // ::rosidl_typesupport_fastrtps_cpp::typesupport_identifier,
    "rosidl_typesupport_introspection_cpp",  // ::rosidl_typesupport_introspection_cpp::typesupport_identifier,
  }
};

typedef struct _MoveToLeft_SendGoal_type_support_symbol_names_t
{
  const char * symbol_name[2];
} _MoveToLeft_SendGoal_type_support_symbol_names_t;

#define STRINGIFY_(s) #s
#define STRINGIFY(s) STRINGIFY_(s)

static const _MoveToLeft_SendGoal_type_support_symbol_names_t _MoveToLeft_SendGoal_service_typesupport_symbol_names = {
  {
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__SERVICE_SYMBOL_NAME(rosidl_typesupport_fastrtps_cpp, interfaces, action, MoveToLeft_SendGoal)),
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__SERVICE_SYMBOL_NAME(rosidl_typesupport_introspection_cpp, interfaces, action, MoveToLeft_SendGoal)),
  }
};

typedef struct _MoveToLeft_SendGoal_type_support_data_t
{
  void * data[2];
} _MoveToLeft_SendGoal_type_support_data_t;

static _MoveToLeft_SendGoal_type_support_data_t _MoveToLeft_SendGoal_service_typesupport_data = {
  {
    0,  // will store the shared library later
    0,  // will store the shared library later
  }
};

static const type_support_map_t _MoveToLeft_SendGoal_service_typesupport_map = {
  2,
  "interfaces",
  &_MoveToLeft_SendGoal_service_typesupport_ids.typesupport_identifier[0],
  &_MoveToLeft_SendGoal_service_typesupport_symbol_names.symbol_name[0],
  &_MoveToLeft_SendGoal_service_typesupport_data.data[0],
};

static const rosidl_service_type_support_t MoveToLeft_SendGoal_service_type_support_handle = {
  ::rosidl_typesupport_cpp::typesupport_identifier,
  reinterpret_cast<const type_support_map_t *>(&_MoveToLeft_SendGoal_service_typesupport_map),
  ::rosidl_typesupport_cpp::get_service_typesupport_handle_function,
};

}  // namespace rosidl_typesupport_cpp

}  // namespace action

}  // namespace interfaces

namespace rosidl_typesupport_cpp
{

template<>
ROSIDL_TYPESUPPORT_CPP_PUBLIC
const rosidl_service_type_support_t *
get_service_type_support_handle<interfaces::action::MoveToLeft_SendGoal>()
{
  return &::interfaces::action::rosidl_typesupport_cpp::MoveToLeft_SendGoal_service_type_support_handle;
}

}  // namespace rosidl_typesupport_cpp

#ifdef __cplusplus
extern "C"
{
#endif

ROSIDL_TYPESUPPORT_CPP_PUBLIC
const rosidl_service_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__SERVICE_SYMBOL_NAME(rosidl_typesupport_cpp, interfaces, action, MoveToLeft_SendGoal)() {
  return ::rosidl_typesupport_cpp::get_service_type_support_handle<interfaces::action::MoveToLeft_SendGoal>();
}

#ifdef __cplusplus
}
#endif

// already included above
// #include "cstddef"
// already included above
// #include "rosidl_runtime_c/message_type_support_struct.h"
// already included above
// #include "interfaces/action/detail/move_to_left__struct.hpp"
// already included above
// #include "rosidl_typesupport_cpp/identifier.hpp"
// already included above
// #include "rosidl_typesupport_cpp/message_type_support.hpp"
// already included above
// #include "rosidl_typesupport_c/type_support_map.h"
// already included above
// #include "rosidl_typesupport_cpp/message_type_support_dispatch.hpp"
// already included above
// #include "rosidl_typesupport_cpp/visibility_control.h"
// already included above
// #include "rosidl_typesupport_interface/macros.h"

namespace interfaces
{

namespace action
{

namespace rosidl_typesupport_cpp
{

typedef struct _MoveToLeft_GetResult_Request_type_support_ids_t
{
  const char * typesupport_identifier[2];
} _MoveToLeft_GetResult_Request_type_support_ids_t;

static const _MoveToLeft_GetResult_Request_type_support_ids_t _MoveToLeft_GetResult_Request_message_typesupport_ids = {
  {
    "rosidl_typesupport_fastrtps_cpp",  // ::rosidl_typesupport_fastrtps_cpp::typesupport_identifier,
    "rosidl_typesupport_introspection_cpp",  // ::rosidl_typesupport_introspection_cpp::typesupport_identifier,
  }
};

typedef struct _MoveToLeft_GetResult_Request_type_support_symbol_names_t
{
  const char * symbol_name[2];
} _MoveToLeft_GetResult_Request_type_support_symbol_names_t;

#define STRINGIFY_(s) #s
#define STRINGIFY(s) STRINGIFY_(s)

static const _MoveToLeft_GetResult_Request_type_support_symbol_names_t _MoveToLeft_GetResult_Request_message_typesupport_symbol_names = {
  {
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_fastrtps_cpp, interfaces, action, MoveToLeft_GetResult_Request)),
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_cpp, interfaces, action, MoveToLeft_GetResult_Request)),
  }
};

typedef struct _MoveToLeft_GetResult_Request_type_support_data_t
{
  void * data[2];
} _MoveToLeft_GetResult_Request_type_support_data_t;

static _MoveToLeft_GetResult_Request_type_support_data_t _MoveToLeft_GetResult_Request_message_typesupport_data = {
  {
    0,  // will store the shared library later
    0,  // will store the shared library later
  }
};

static const type_support_map_t _MoveToLeft_GetResult_Request_message_typesupport_map = {
  2,
  "interfaces",
  &_MoveToLeft_GetResult_Request_message_typesupport_ids.typesupport_identifier[0],
  &_MoveToLeft_GetResult_Request_message_typesupport_symbol_names.symbol_name[0],
  &_MoveToLeft_GetResult_Request_message_typesupport_data.data[0],
};

static const rosidl_message_type_support_t MoveToLeft_GetResult_Request_message_type_support_handle = {
  ::rosidl_typesupport_cpp::typesupport_identifier,
  reinterpret_cast<const type_support_map_t *>(&_MoveToLeft_GetResult_Request_message_typesupport_map),
  ::rosidl_typesupport_cpp::get_message_typesupport_handle_function,
};

}  // namespace rosidl_typesupport_cpp

}  // namespace action

}  // namespace interfaces

namespace rosidl_typesupport_cpp
{

template<>
ROSIDL_TYPESUPPORT_CPP_PUBLIC
const rosidl_message_type_support_t *
get_message_type_support_handle<interfaces::action::MoveToLeft_GetResult_Request>()
{
  return &::interfaces::action::rosidl_typesupport_cpp::MoveToLeft_GetResult_Request_message_type_support_handle;
}

#ifdef __cplusplus
extern "C"
{
#endif

ROSIDL_TYPESUPPORT_CPP_PUBLIC
const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_cpp, interfaces, action, MoveToLeft_GetResult_Request)() {
  return get_message_type_support_handle<interfaces::action::MoveToLeft_GetResult_Request>();
}

#ifdef __cplusplus
}
#endif
}  // namespace rosidl_typesupport_cpp

// already included above
// #include "cstddef"
// already included above
// #include "rosidl_runtime_c/message_type_support_struct.h"
// already included above
// #include "interfaces/action/detail/move_to_left__struct.hpp"
// already included above
// #include "rosidl_typesupport_cpp/identifier.hpp"
// already included above
// #include "rosidl_typesupport_cpp/message_type_support.hpp"
// already included above
// #include "rosidl_typesupport_c/type_support_map.h"
// already included above
// #include "rosidl_typesupport_cpp/message_type_support_dispatch.hpp"
// already included above
// #include "rosidl_typesupport_cpp/visibility_control.h"
// already included above
// #include "rosidl_typesupport_interface/macros.h"

namespace interfaces
{

namespace action
{

namespace rosidl_typesupport_cpp
{

typedef struct _MoveToLeft_GetResult_Response_type_support_ids_t
{
  const char * typesupport_identifier[2];
} _MoveToLeft_GetResult_Response_type_support_ids_t;

static const _MoveToLeft_GetResult_Response_type_support_ids_t _MoveToLeft_GetResult_Response_message_typesupport_ids = {
  {
    "rosidl_typesupport_fastrtps_cpp",  // ::rosidl_typesupport_fastrtps_cpp::typesupport_identifier,
    "rosidl_typesupport_introspection_cpp",  // ::rosidl_typesupport_introspection_cpp::typesupport_identifier,
  }
};

typedef struct _MoveToLeft_GetResult_Response_type_support_symbol_names_t
{
  const char * symbol_name[2];
} _MoveToLeft_GetResult_Response_type_support_symbol_names_t;

#define STRINGIFY_(s) #s
#define STRINGIFY(s) STRINGIFY_(s)

static const _MoveToLeft_GetResult_Response_type_support_symbol_names_t _MoveToLeft_GetResult_Response_message_typesupport_symbol_names = {
  {
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_fastrtps_cpp, interfaces, action, MoveToLeft_GetResult_Response)),
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_cpp, interfaces, action, MoveToLeft_GetResult_Response)),
  }
};

typedef struct _MoveToLeft_GetResult_Response_type_support_data_t
{
  void * data[2];
} _MoveToLeft_GetResult_Response_type_support_data_t;

static _MoveToLeft_GetResult_Response_type_support_data_t _MoveToLeft_GetResult_Response_message_typesupport_data = {
  {
    0,  // will store the shared library later
    0,  // will store the shared library later
  }
};

static const type_support_map_t _MoveToLeft_GetResult_Response_message_typesupport_map = {
  2,
  "interfaces",
  &_MoveToLeft_GetResult_Response_message_typesupport_ids.typesupport_identifier[0],
  &_MoveToLeft_GetResult_Response_message_typesupport_symbol_names.symbol_name[0],
  &_MoveToLeft_GetResult_Response_message_typesupport_data.data[0],
};

static const rosidl_message_type_support_t MoveToLeft_GetResult_Response_message_type_support_handle = {
  ::rosidl_typesupport_cpp::typesupport_identifier,
  reinterpret_cast<const type_support_map_t *>(&_MoveToLeft_GetResult_Response_message_typesupport_map),
  ::rosidl_typesupport_cpp::get_message_typesupport_handle_function,
};

}  // namespace rosidl_typesupport_cpp

}  // namespace action

}  // namespace interfaces

namespace rosidl_typesupport_cpp
{

template<>
ROSIDL_TYPESUPPORT_CPP_PUBLIC
const rosidl_message_type_support_t *
get_message_type_support_handle<interfaces::action::MoveToLeft_GetResult_Response>()
{
  return &::interfaces::action::rosidl_typesupport_cpp::MoveToLeft_GetResult_Response_message_type_support_handle;
}

#ifdef __cplusplus
extern "C"
{
#endif

ROSIDL_TYPESUPPORT_CPP_PUBLIC
const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_cpp, interfaces, action, MoveToLeft_GetResult_Response)() {
  return get_message_type_support_handle<interfaces::action::MoveToLeft_GetResult_Response>();
}

#ifdef __cplusplus
}
#endif
}  // namespace rosidl_typesupport_cpp

// already included above
// #include "cstddef"
// already included above
// #include "rosidl_runtime_c/service_type_support_struct.h"
// already included above
// #include "interfaces/action/detail/move_to_left__struct.hpp"
// already included above
// #include "rosidl_typesupport_cpp/identifier.hpp"
// already included above
// #include "rosidl_typesupport_cpp/service_type_support.hpp"
// already included above
// #include "rosidl_typesupport_c/type_support_map.h"
// already included above
// #include "rosidl_typesupport_cpp/service_type_support_dispatch.hpp"
// already included above
// #include "rosidl_typesupport_cpp/visibility_control.h"
// already included above
// #include "rosidl_typesupport_interface/macros.h"

namespace interfaces
{

namespace action
{

namespace rosidl_typesupport_cpp
{

typedef struct _MoveToLeft_GetResult_type_support_ids_t
{
  const char * typesupport_identifier[2];
} _MoveToLeft_GetResult_type_support_ids_t;

static const _MoveToLeft_GetResult_type_support_ids_t _MoveToLeft_GetResult_service_typesupport_ids = {
  {
    "rosidl_typesupport_fastrtps_cpp",  // ::rosidl_typesupport_fastrtps_cpp::typesupport_identifier,
    "rosidl_typesupport_introspection_cpp",  // ::rosidl_typesupport_introspection_cpp::typesupport_identifier,
  }
};

typedef struct _MoveToLeft_GetResult_type_support_symbol_names_t
{
  const char * symbol_name[2];
} _MoveToLeft_GetResult_type_support_symbol_names_t;

#define STRINGIFY_(s) #s
#define STRINGIFY(s) STRINGIFY_(s)

static const _MoveToLeft_GetResult_type_support_symbol_names_t _MoveToLeft_GetResult_service_typesupport_symbol_names = {
  {
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__SERVICE_SYMBOL_NAME(rosidl_typesupport_fastrtps_cpp, interfaces, action, MoveToLeft_GetResult)),
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__SERVICE_SYMBOL_NAME(rosidl_typesupport_introspection_cpp, interfaces, action, MoveToLeft_GetResult)),
  }
};

typedef struct _MoveToLeft_GetResult_type_support_data_t
{
  void * data[2];
} _MoveToLeft_GetResult_type_support_data_t;

static _MoveToLeft_GetResult_type_support_data_t _MoveToLeft_GetResult_service_typesupport_data = {
  {
    0,  // will store the shared library later
    0,  // will store the shared library later
  }
};

static const type_support_map_t _MoveToLeft_GetResult_service_typesupport_map = {
  2,
  "interfaces",
  &_MoveToLeft_GetResult_service_typesupport_ids.typesupport_identifier[0],
  &_MoveToLeft_GetResult_service_typesupport_symbol_names.symbol_name[0],
  &_MoveToLeft_GetResult_service_typesupport_data.data[0],
};

static const rosidl_service_type_support_t MoveToLeft_GetResult_service_type_support_handle = {
  ::rosidl_typesupport_cpp::typesupport_identifier,
  reinterpret_cast<const type_support_map_t *>(&_MoveToLeft_GetResult_service_typesupport_map),
  ::rosidl_typesupport_cpp::get_service_typesupport_handle_function,
};

}  // namespace rosidl_typesupport_cpp

}  // namespace action

}  // namespace interfaces

namespace rosidl_typesupport_cpp
{

template<>
ROSIDL_TYPESUPPORT_CPP_PUBLIC
const rosidl_service_type_support_t *
get_service_type_support_handle<interfaces::action::MoveToLeft_GetResult>()
{
  return &::interfaces::action::rosidl_typesupport_cpp::MoveToLeft_GetResult_service_type_support_handle;
}

}  // namespace rosidl_typesupport_cpp

#ifdef __cplusplus
extern "C"
{
#endif

ROSIDL_TYPESUPPORT_CPP_PUBLIC
const rosidl_service_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__SERVICE_SYMBOL_NAME(rosidl_typesupport_cpp, interfaces, action, MoveToLeft_GetResult)() {
  return ::rosidl_typesupport_cpp::get_service_type_support_handle<interfaces::action::MoveToLeft_GetResult>();
}

#ifdef __cplusplus
}
#endif

// already included above
// #include "cstddef"
// already included above
// #include "rosidl_runtime_c/message_type_support_struct.h"
// already included above
// #include "interfaces/action/detail/move_to_left__struct.hpp"
// already included above
// #include "rosidl_typesupport_cpp/identifier.hpp"
// already included above
// #include "rosidl_typesupport_cpp/message_type_support.hpp"
// already included above
// #include "rosidl_typesupport_c/type_support_map.h"
// already included above
// #include "rosidl_typesupport_cpp/message_type_support_dispatch.hpp"
// already included above
// #include "rosidl_typesupport_cpp/visibility_control.h"
// already included above
// #include "rosidl_typesupport_interface/macros.h"

namespace interfaces
{

namespace action
{

namespace rosidl_typesupport_cpp
{

typedef struct _MoveToLeft_FeedbackMessage_type_support_ids_t
{
  const char * typesupport_identifier[2];
} _MoveToLeft_FeedbackMessage_type_support_ids_t;

static const _MoveToLeft_FeedbackMessage_type_support_ids_t _MoveToLeft_FeedbackMessage_message_typesupport_ids = {
  {
    "rosidl_typesupport_fastrtps_cpp",  // ::rosidl_typesupport_fastrtps_cpp::typesupport_identifier,
    "rosidl_typesupport_introspection_cpp",  // ::rosidl_typesupport_introspection_cpp::typesupport_identifier,
  }
};

typedef struct _MoveToLeft_FeedbackMessage_type_support_symbol_names_t
{
  const char * symbol_name[2];
} _MoveToLeft_FeedbackMessage_type_support_symbol_names_t;

#define STRINGIFY_(s) #s
#define STRINGIFY(s) STRINGIFY_(s)

static const _MoveToLeft_FeedbackMessage_type_support_symbol_names_t _MoveToLeft_FeedbackMessage_message_typesupport_symbol_names = {
  {
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_fastrtps_cpp, interfaces, action, MoveToLeft_FeedbackMessage)),
    STRINGIFY(ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_introspection_cpp, interfaces, action, MoveToLeft_FeedbackMessage)),
  }
};

typedef struct _MoveToLeft_FeedbackMessage_type_support_data_t
{
  void * data[2];
} _MoveToLeft_FeedbackMessage_type_support_data_t;

static _MoveToLeft_FeedbackMessage_type_support_data_t _MoveToLeft_FeedbackMessage_message_typesupport_data = {
  {
    0,  // will store the shared library later
    0,  // will store the shared library later
  }
};

static const type_support_map_t _MoveToLeft_FeedbackMessage_message_typesupport_map = {
  2,
  "interfaces",
  &_MoveToLeft_FeedbackMessage_message_typesupport_ids.typesupport_identifier[0],
  &_MoveToLeft_FeedbackMessage_message_typesupport_symbol_names.symbol_name[0],
  &_MoveToLeft_FeedbackMessage_message_typesupport_data.data[0],
};

static const rosidl_message_type_support_t MoveToLeft_FeedbackMessage_message_type_support_handle = {
  ::rosidl_typesupport_cpp::typesupport_identifier,
  reinterpret_cast<const type_support_map_t *>(&_MoveToLeft_FeedbackMessage_message_typesupport_map),
  ::rosidl_typesupport_cpp::get_message_typesupport_handle_function,
};

}  // namespace rosidl_typesupport_cpp

}  // namespace action

}  // namespace interfaces

namespace rosidl_typesupport_cpp
{

template<>
ROSIDL_TYPESUPPORT_CPP_PUBLIC
const rosidl_message_type_support_t *
get_message_type_support_handle<interfaces::action::MoveToLeft_FeedbackMessage>()
{
  return &::interfaces::action::rosidl_typesupport_cpp::MoveToLeft_FeedbackMessage_message_type_support_handle;
}

#ifdef __cplusplus
extern "C"
{
#endif

ROSIDL_TYPESUPPORT_CPP_PUBLIC
const rosidl_message_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__MESSAGE_SYMBOL_NAME(rosidl_typesupport_cpp, interfaces, action, MoveToLeft_FeedbackMessage)() {
  return get_message_type_support_handle<interfaces::action::MoveToLeft_FeedbackMessage>();
}

#ifdef __cplusplus
}
#endif
}  // namespace rosidl_typesupport_cpp

#include "action_msgs/msg/goal_status_array.hpp"
#include "action_msgs/srv/cancel_goal.hpp"
// already included above
// #include "interfaces/action/detail/move_to_left__struct.hpp"
// already included above
// #include "rosidl_typesupport_cpp/visibility_control.h"
#include "rosidl_runtime_c/action_type_support_struct.h"
#include "rosidl_typesupport_cpp/action_type_support.hpp"
// already included above
// #include "rosidl_typesupport_cpp/message_type_support.hpp"
// already included above
// #include "rosidl_typesupport_cpp/service_type_support.hpp"

namespace interfaces
{

namespace action
{

namespace rosidl_typesupport_cpp
{

static rosidl_action_type_support_t MoveToLeft_action_type_support_handle = {
  NULL, NULL, NULL, NULL, NULL};

}  // namespace rosidl_typesupport_cpp

}  // namespace action

}  // namespace interfaces

namespace rosidl_typesupport_cpp
{

template<>
ROSIDL_TYPESUPPORT_CPP_PUBLIC
const rosidl_action_type_support_t *
get_action_type_support_handle<interfaces::action::MoveToLeft>()
{
  using ::interfaces::action::rosidl_typesupport_cpp::MoveToLeft_action_type_support_handle;
  // Thread-safe by always writing the same values to the static struct
  MoveToLeft_action_type_support_handle.goal_service_type_support = get_service_type_support_handle<::interfaces::action::MoveToLeft::Impl::SendGoalService>();
  MoveToLeft_action_type_support_handle.result_service_type_support = get_service_type_support_handle<::interfaces::action::MoveToLeft::Impl::GetResultService>();
  MoveToLeft_action_type_support_handle.cancel_service_type_support = get_service_type_support_handle<::interfaces::action::MoveToLeft::Impl::CancelGoalService>();
  MoveToLeft_action_type_support_handle.feedback_message_type_support = get_message_type_support_handle<::interfaces::action::MoveToLeft::Impl::FeedbackMessage>();
  MoveToLeft_action_type_support_handle.status_message_type_support = get_message_type_support_handle<::interfaces::action::MoveToLeft::Impl::GoalStatusMessage>();
  return &MoveToLeft_action_type_support_handle;
}

}  // namespace rosidl_typesupport_cpp

#ifdef __cplusplus
extern "C"
{
#endif

ROSIDL_TYPESUPPORT_CPP_PUBLIC
const rosidl_action_type_support_t *
ROSIDL_TYPESUPPORT_INTERFACE__ACTION_SYMBOL_NAME(rosidl_typesupport_cpp, interfaces, action, MoveToLeft)() {
  return ::rosidl_typesupport_cpp::get_action_type_support_handle<interfaces::action::MoveToLeft>();
}

#ifdef __cplusplus
}
#endif
