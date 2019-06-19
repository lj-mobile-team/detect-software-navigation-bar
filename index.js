import { NativeModules } from 'react-native';

const { RNDetectSoftwareNavigationBar } = NativeModules;

const callbac = bool => bool;

const isSoftware = RNDetectSoftwareNavigationBar.isSoftware(callbac);

console.log(isSoftware);

export default isSoftware;
