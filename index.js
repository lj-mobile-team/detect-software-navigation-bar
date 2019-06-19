import { NativeModules } from 'react-native';

const { RNDetectSoftwareNavigationBar } = NativeModules;

const isSoftware = RNDetectSoftwareNavigationBar.isSoftware();

export default isSoftware();
