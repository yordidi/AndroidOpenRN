import {NativeEventEmitter, NativeModules} from 'react-native';

export const nativeEventEmitter = new NativeEventEmitter(
  NativeModules.NativeEventToRN,
);
