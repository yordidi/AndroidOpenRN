import {NativeModules} from 'react-native';

export const getIntentData = params => {
  return NativeModules.NativeEventToRN.getIntentData();
};

export const popReactNative = params => {
  return NativeModules.NativeEventToRN.popReactNative();
};

export const popReactNativeWithResult = params => {
  return NativeModules.NativeEventToRN.popReactNativeWithResult(params);
};
