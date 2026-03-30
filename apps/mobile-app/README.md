# MyFinanceManager Mobile

React Native mobile application built with Expo, following Mobile-First design principles.

## Tech Stack

- **React Native** - Cross-platform mobile development
- **Expo** - Development platform and tools
- **Expo Router** - File-based routing
- **TypeScript** - Type safety
- **NativeWind** - Tailwind CSS for React Native
- **Gluestack UI** - Component library
- **Zustand** - State management

## Project Structure

```
src/
├── components/          # Reusable UI components
├── screens/            # Screen components (pages)
├── hooks/              # Custom React hooks
├── store/              # Zustand stores
├── services/           # API services
├── utils/              # Utility functions
├── types/              # TypeScript type definitions
└── constants/          # App constants

app/                    # Expo Router pages
├── (tabs)/            # Tab navigation screens
├── (auth)/            # Authentication screens
└── _layout.tsx        # Root layout
```

## Getting Started

### Prerequisites

- Node.js 18+
- npm or yarn
- Expo CLI
- iOS Simulator or Android Emulator (optional)

### Installation

1. Install dependencies:
   ```bash
   npm install
   ```

2. Copy environment file:
   ```bash
   cp .env.example .env
   ```

3. Start the development server:
   ```bash
   npm start
   ```

4. Run on platform:
   - Press `i` for iOS simulator
   - Press `a` for Android emulator
   - Press `w` for web browser
   - Scan QR code with Expo Go app on physical device

## Available Scripts

| Script | Description |
|--------|-------------|
| `npm start` | Start Expo development server |
| `npm run ios` | Run on iOS simulator |
| `npm run android` | Run on Android emulator |
| `npm run web` | Run in web browser |
| `npm run lint` | Run ESLint |
| `npm run format` | Format code with Prettier |
| `npm test` | Run Jest tests |
| `npm run test:coverage` | Generate coverage report |

## Quality Tools

### Linting
```bash
npm run lint
```

### Formatting
```bash
npm run format
```

### Testing
```bash
npm test
npm run test:watch
npm run test:coverage
```

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `API_BASE_URL` | Backend API URL | `http://localhost:8080/api/v1` |
| `APP_NAME` | Application name | `MyFinanceManager` |
| `APP_VERSION` | Application version | `0.1.0` |

## Styling

The app uses **NativeWind** (Tailwind CSS for React Native) for styling:

```tsx
import { View, Text } from 'react-native';

export function MyComponent() {
  return (
    <View className="flex-1 items-center justify-center bg-white">
      <Text className="text-lg font-bold text-gray-900">
        Hello World
      </Text>
    </View>
  );
}
```

## State Management

State is managed with **Zustand**:

```tsx
import { create } from 'zustand';

interface AuthStore {
  user: User | null;
  token: string | null;
  login: (user: User, token: string) => void;
  logout: () => void;
}

export const useAuthStore = create<AuthStore>((set) => ({
  user: null,
  token: null,
  login: (user, token) => set({ user, token }),
  logout: () => set({ user: null, token: null }),
}));
```

## API Integration

API calls are made through services in `src/services/`:

```tsx
import { api } from './api';

export const authAPI = {
  login: async (email: string, password: string) => {
    const response = await api.post('/auth/login', { email, password });
    return response.data;
  },
  register: async (data: RegisterData) => {
    const response = await api.post('/auth/register', data);
    return response.data;
  },
};
```

## License

Private - All rights reserved