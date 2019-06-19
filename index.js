import { NativeModules } from 'react-native';

const { RNDetectSoftwareNavigationBar } = NativeModules;

let isSoftware = false;

const callbac = bool => {
  isSoftware = bool;
};
RNDetectSoftwareNavigationBar.isSoftware(callbac);

console.log(isSoftware);

export default isSoftware;
