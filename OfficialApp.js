/**
 * 单纯渲染一个RN页面，不接受数据
 */
import React from 'react';
import {View, Text, StyleSheet} from 'react-native';
import {NavigationContainer} from '@react-navigation/native';
import {createNativeStackNavigator} from '@react-navigation/native-stack';

function HomeScreen() {
  return (
    <View style={styles.box}>
      <Text>Home!</Text>
    </View>
  );
}
function SettingScreen() {
  return (
    <View style={styles.box}>
      <Text>Settings!</Text>
    </View>
  );
}

const Stack = createNativeStackNavigator();

export default function OfficialApp() {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen name="Home" component={HomeScreen} />
        <Stack.Screen name="Settings" component={SettingScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}

const styles = StyleSheet.create({
  box: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
});
