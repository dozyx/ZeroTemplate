package com.zerofate.java;

/**
 * @author dozeboy
 * @date 2018/3/14
 */

public class N {

    /**
     * data : {"name":"张三","phone":"110","address":{"province":"江苏","city":"常州"}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 张三
         * phone : 110
         * address : {"province":"江苏","city":"常州"}
         */

        private String name;
        private String phone;
        private AddressBean address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public static class AddressBean {
            /**
             * province : 江苏
             * city : 常州
             */

            private String province;
            private String city;

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }
        }
    }
}
