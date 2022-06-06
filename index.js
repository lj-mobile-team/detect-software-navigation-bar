import { NativeModules, Platform } from 'react-native';

const DetectModule = NativeModules.RNDetectSoftwareNavigationBar;

function isModuleAvailable() {
  if (Platform.OS !== 'android') return false;

  if (!DetectModule) {
    throw "RNDetectSoftwareNavigationBar not defined. Try rebuilding your project. e.g. react-native run-android";
  }

  return true;
}

function withModule(callback) {
  try {
    if (!isModuleAvailable() || typeof callback !== 'function') {
      return 0;
    } else {
      return callback();
    }
  } catch (e) {
    console.error(e);
    return 0;
  }
}

export function get(dim) {
  return withModule(() => DetectModule[dim]);
}

export function getRealWindowHeight() {
  return withModule(() => DetectModule.getRealHeight());
}

export function getRealWindowWidth() {
  return withModule(() => DetectModule.getRealWidth());
}

export function getStatusBarHeight() {
  return withModule(() => DetectModule.getStatusBarHeight());
}

export function getSoftMenuBarHeight() {
  return withModule(() => DetectModule.getSoftMenuBarHeight());
}

export function getSmartBarHeight() {
  return withModule(() => DetectModule.getSmartBarHeight());
}

export function isSoftMenuBarEnabled() {
  return get('SOFT_MENU_BAR_ENABLED');
}

export function isEmulator() {
  return get('IS_EMULATOR');
}

export function setNavBackgroundColor(color = '#ffffff') {
  return withModule(() => DetectModule.setNavBackgroundColor(color));
}

// stay compatible with pre-es6 exports
export default {
  get,
  getRealWindowHeight,
  getRealWindowWidth,
  getStatusBarHeight,
  getSoftMenuBarHeight,
  getSmartBarHeight,
  isSoftMenuBarEnabled,
  isEmulator,
  setNavBackgroundColor,
}